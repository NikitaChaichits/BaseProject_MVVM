package com.baseproject.data.source.remote.api

import com.fahrer.data.model.pojo.login.LoginResponse
import okhttp3.Credentials
import retrofit2.http.HeaderMap
import retrofit2.http.POST

interface AuthApi {

    companion object {
        const val AUTH_TOKEN_HEADER_KEY = "X-Token"
        const val AUTH_CREDENTIALS_HEADER_KEY = "Authorization"

        const val API_REFRESH_RELATIVE_PATH = "/api/v1/users/refresh"
    }

    class LoginHeaders private constructor(headers: Map<String, String>) : Map<String, String> by headers {

        constructor(login: String, password: String) : this(
            mapOf(AUTH_CREDENTIALS_HEADER_KEY to Credentials.basic(login, password))
        )
    }

    @POST("/api/v1/users/login")
    suspend fun login(@HeaderMap headers: LoginHeaders): Result<LoginResponse>


}