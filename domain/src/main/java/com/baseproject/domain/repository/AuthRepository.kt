package com.baseproject.domain.repository

import com.baseproject.domain.model.dto.Tokens
import com.baseproject.domain.model.result.Result
import kotlinx.coroutines.flow.SharedFlow

interface AuthRepository {

    suspend fun login(login: String, password: String): Result<Tokens>

    suspend fun verifyUser(
        token: String,
        password: String,
        passwordConfirmation: String
    ): Result<Unit>

    suspend fun resetPassword(
        login: String,
        token: String,
        password: String,
        passwordConfirmation: String
    ): Result<Unit>

    suspend fun forgotPassword(login: String): Result<Unit>

    suspend fun refreshTokens(refreshToken: String): Result<Tokens>

    suspend fun logout(): Result<Unit>

    val unauthorisedEvent: SharedFlow<Unit>

    suspend fun reportUnauthorisedException()
}