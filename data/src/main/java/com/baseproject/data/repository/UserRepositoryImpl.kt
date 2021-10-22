package com.fahrer.data.repository

import com.fahrer.data.mapper.mapToDomain
import com.fahrer.data.mapper.mapToEntity
import com.fahrer.data.model.pojo.user.UserData
import com.fahrer.data.source.local.SharedPreferencesDataSource
import com.fahrer.data.source.local.db.dao.UsersDao
import com.fahrer.data.source.local.db.entities.UserEntityWithDispositionLocations
import com.fahrer.data.source.remote.api.UserApi
import com.fahrer.data.util.constraintSafeDbAction
import com.fahrer.domain.model.dto.Tokens
import com.fahrer.domain.model.dto.User
import com.fahrer.domain.model.result.Result
import com.fahrer.domain.model.result.chain
import com.fahrer.domain.model.result.map
import com.fahrer.domain.repository.UserRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val api: UserApi,
    private val sharedPrefs: SharedPreferencesDataSource,
    private val dao: UsersDao,
    private val defaultDispatcher: CoroutineDispatcher = Dispatchers.Default
) : UserRepository {

    private val _loggedInState = MutableStateFlow(isUserLoggedIn())
    override val loggedInState: StateFlow<Boolean> = _loggedInState

    override fun getUsers(): Flow<List<User>> =
        dao.getAllUsers().map { entitiesList ->
            entitiesList.map { it.mapToDomain() }
        }

    override fun getUserById(id: String): Flow<User> =
        dao.getUserDistinctUntilChanged(id).map { it.mapToDomain() }

    override fun getUserData(): Flow<User?> {
        return dao.getAllUsers().map { it.firstOrNull()?.mapToDomain() }
    }

    override suspend fun getUserDataSynchronously(): User? = withContext(defaultDispatcher) {
        return@withContext dao.getAllUsersSynchronously().firstOrNull()?.mapToDomain()
    }

    override fun isUserLoggedIn(): Boolean {
        return sharedPrefs.getAuthTokens() != null
    }

    override fun startUserSession(tokens: Tokens) {
        sharedPrefs.setAuthTokens(tokens)

        _loggedInState.value = true
    }

    override suspend fun endUserSession() {
        sharedPrefs.wipeAuthTokens()
        _loggedInState.value = false
        deleteAllUsersData()
    }

    override fun getSessionTokens(): Tokens? = sharedPrefs.getAuthTokens()

    override suspend fun syncUserData(): Result<Unit> = withContext(defaultDispatcher) {
        return@withContext api.getUserData().map { it.data }
            .chain { syncUsers(listOf(it)) }
    }

    private suspend fun syncUsers(remoteUsers: List<UserData>): Result<Unit> {
        val id2UserMap: MutableMap<String, UserData> =
            remoteUsers.associateBy({ it.id }, { it }).toMutableMap()

        val localUsers: List<UserEntityWithDispositionLocations> = dao.getAllUsersSynchronously()
        localUsers.forEach { localUser ->
            val remoteUser: UserData? = id2UserMap.remove(localUser.user.id)
            if (remoteUser != null) {
                val userEntity = remoteUser.mapToEntity()
//                val dispositionLocationIds = remoteUser.dispositionLocations.map { it.id }
//                dao.updateUserData(userEntity, dispositionLocationIds)
                dao.updateUserData(userEntity)
            } else {
                constraintSafeDbAction { dao.deleteUser(localUser.user.id) }
            }
        }
        id2UserMap.values.forEach { newRemoteUser ->
            val userEntity = newRemoteUser.mapToEntity()
//            val dispositionLocationIds = newRemoteUser.dispositionLocations.map { it.id }
            constraintSafeDbAction {
//                dao.insertUserData(userEntity, dispositionLocationIds)
                dao.insertUserData(userEntity)
            }
        }
        return Result.Success(Unit)
    }

    private suspend fun deleteAllUsersData() {
        dao.deleteAll()
    }
}