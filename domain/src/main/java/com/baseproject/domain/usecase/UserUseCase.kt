package com.fahrer.domain.usecase

import com.fahrer.domain.model.dto.Tokens
import com.fahrer.domain.model.dto.User
import com.fahrer.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

class UserUseCase @Inject constructor(
    private val userRepository: UserRepository
) {

    val loggedInState: StateFlow<Boolean> = userRepository.loggedInState
    fun isUserLoggedIn(): Boolean = userRepository.isUserLoggedIn()

    fun startUserSession(tokens: Tokens) = userRepository.startUserSession(tokens)

    suspend fun endUserSession() = userRepository.endUserSession()

    val sessionTokens: Tokens?
        get() = userRepository.getSessionTokens()

    fun getUserData(): Flow<User?> = userRepository.getUserData()

    suspend fun getUserDataSynchronously(): User? = userRepository.getUserDataSynchronously()

    suspend fun syncUserData() = userRepository.syncUserData()
}