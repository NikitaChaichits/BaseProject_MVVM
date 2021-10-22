package com.fahrer.data.repository

import com.fahrer.data.mapper.mapToTokens
import com.fahrer.data.model.pojo.forgotpassword.ForgotPasswordRequest
import com.fahrer.data.model.pojo.resetpassword.ResetPasswordRequest
import com.fahrer.data.model.pojo.verify.VerifyUserRequest
import com.fahrer.data.source.local.SharedPreferencesDataSource
import com.fahrer.data.source.remote.api.AuthApi
import com.fahrer.domain.model.dto.Tokens
import com.fahrer.domain.model.result.Result
import com.fahrer.domain.model.result.map
import com.fahrer.domain.model.result.toUnitResult
import com.fahrer.domain.repository.AuthRepository
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val api: AuthApi,
    private val sharedPrefs: SharedPreferencesDataSource
) : AuthRepository {

    override suspend fun login(login: String, password: String): Result<Tokens> {
        return api.login(AuthApi.LoginHeaders(login, password)).map { it.mapToTokens() }
    }

    override suspend fun verifyUser(
        token: String,
        password: String,
        passwordConfirmation: String
    ): Result<Unit> {
        val request = VerifyUserRequest(token, password, passwordConfirmation)
        return api.verify(request).toUnitResult()
    }

    override suspend fun resetPassword(
        login: String,
        token: String,
        password: String,
        passwordConfirmation: String
    ): Result<Unit> {
        val request = ResetPasswordRequest(login, token, password, passwordConfirmation)
        return api.resetPassword(request).toUnitResult()
    }

    override suspend fun forgotPassword(login: String): Result<Unit> {
        val request = ForgotPasswordRequest(login)
        return api.forgotPassword(request).toUnitResult()
    }

    override suspend fun refreshTokens(refreshToken: String): Result<Tokens> {
        return api.refreshToken(refreshToken).map { it.mapToTokens() }
    }

    override suspend fun logout(): Result<Unit> {
        return api.logout().toUnitResult()
    }

    private val _unauthorisedEvent = MutableSharedFlow<Unit>()

    override val unauthorisedEvent = _unauthorisedEvent.asSharedFlow()

    override suspend fun reportUnauthorisedException() {
        _unauthorisedEvent.emit(Unit)
    }

    override fun setCompanySubdomain(companyCode: String) {
        sharedPrefs.setCompanyUrlSubdomain(companyCode)
    }

    override fun getCompanySubdomain(): String? = sharedPrefs.getCompanyUrlSubdomain()
}