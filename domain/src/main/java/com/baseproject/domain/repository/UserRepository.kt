package com.baseproject.domain.repository

import com.baseproject.domain.model.dto.Tokens
import com.baseproject.domain.model.dto.User
import com.baseproject.domain.model.result.Result
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

interface UserRepository {

    val loggedInState: StateFlow<Boolean>

    fun getUserData(): Flow<User?>

    suspend fun getUserDataSynchronously(): User?

    fun isUserLoggedIn(): Boolean

    fun startUserSession(tokens: Tokens)

    suspend fun endUserSession()

    fun getSessionTokens(): Tokens?

    fun getUsers(): Flow<List<User>>

    fun getUserById(id : String): Flow<User>

    suspend fun syncUserData(): Result<Unit>

}