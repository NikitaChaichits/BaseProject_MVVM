package com.baseproject.domain.interactor

import com.fahrer.domain.model.dto.Tokens
import com.fahrer.domain.model.result.Result
import com.fahrer.domain.repository.AuthRepository
import com.fahrer.domain.usecase.UserUseCase
import javax.inject.Inject

class SessionInteractor @Inject constructor(
    private val repository: AuthRepository,
    private val userUseCase: UserUseCase,
) {

    val sessionTokens: Tokens?
        get() = userUseCase.sessionTokens

    suspend fun refreshToken(refreshToken: String): Result<Tokens> = repository.refreshTokens(refreshToken)

    fun startUserSession(tokens: Tokens) = userUseCase.startUserSession(tokens)

    suspend fun reportUnauthorisedException() = repository.reportUnauthorisedException()

}