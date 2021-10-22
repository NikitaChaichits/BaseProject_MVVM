package com.fahrer.domain.interactor

import com.fahrer.domain.model.dto.Tokens
import com.fahrer.domain.model.result.Result
import com.fahrer.domain.model.result.onSuccess
import com.fahrer.domain.repository.AuthRepository
import com.fahrer.domain.usecase.UserUseCase
import javax.inject.Inject

class AuthInteractor @Inject constructor(
    private val repository: AuthRepository,
    private val userUseCase: UserUseCase,
) {

    suspend fun login(companyCode: String, login: String, password: String): Result<Tokens> {
        repository.setCompanySubdomain(companyCode)
        return repository.login(login, password)
            .onSuccess { tokens -> userUseCase.startUserSession(tokens) }
    }

    suspend fun verifyUser(
        companyCode: String,
        token: String,
        password: String,
        passwordConfirmation: String
    ): Result<Unit> {
        repository.setCompanySubdomain(companyCode)
        return repository.verifyUser(token, password, passwordConfirmation)
    }

    suspend fun resetPassword(
        companyCode: String,
        login: String,
        token: String,
        password: String,
        passwordConfirmation: String
    ): Result<Unit> {
        repository.setCompanySubdomain(companyCode)
        return repository.resetPassword(login, token, password, passwordConfirmation)
    }

    suspend fun forgotPassword(companyCode: String, login: String): Result<Unit> {
        repository.setCompanySubdomain(companyCode)
        return repository.forgotPassword(login)
    }

    val companySubdomain: String?
        get() = repository.getCompanySubdomain()
}