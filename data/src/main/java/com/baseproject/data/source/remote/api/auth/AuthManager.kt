package com.baseproject.data.source.remote.api.auth

import com.baseproject.data.source.remote.api.AuthApi
import com.baseproject.domain.interactor.SessionInteractor
import com.baseproject.domain.model.result.onSuccess
import dagger.Lazy
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import okhttp3.Authenticator
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route
import javax.inject.Inject

class AuthManager @Inject constructor(
    // using lazy dependency here to avoid dependency cycle
    private val sessionInteractorLazy: Lazy<SessionInteractor>
) : Authenticator, Interceptor {

    private val sessionInteractor: SessionInteractor
        get() = sessionInteractorLazy.get()

    private val mutex = Mutex()

    override fun authenticate(route: Route?, response: Response): Request? {
        return runBlocking {
            if (response.request.isRefreshRequest()) {
                // For some reason API returns 401 code for refresh requests when the refresh token is expired or invalid.
                // Therefore if we get there after the user/refresh request, it means that token is expired
                reportUnauthorisedException()
                return@runBlocking null
            }

            mutex.withLock {
                val currentTokens = sessionInteractor.sessionTokens ?: return@runBlocking null

                if (currentTokens.accessToken == response.request.headers[AuthApi.AUTH_TOKEN_HEADER_KEY]) {
                    // Have to refresh the token.
                    sessionInteractor.refreshToken(currentTokens.refreshToken)
                        .onSuccess { refreshedTokens ->
                            sessionInteractor.startUserSession(refreshedTokens)
                            return@runBlocking retryWithToken(response, refreshedTokens.accessToken)
                        }
                    // Error fallback.
                    // Currently disabled, since refresh token API returns 401 (unauthorised) when something is wrong.
                    // So the fallback will be triggered in related authenticate() call.
                    //reportUnauthorisedException()
                    return@runBlocking null
                } else {
                    // The request has old auth token, resend it with the actual one.
                    return@runBlocking retryWithToken(response, currentTokens.accessToken)
                }
            }
        }
    }

    private fun retryWithToken(response: Response, accessToken: String): Request {
        return response.request.newBuilder()
            .header(AuthApi.AUTH_TOKEN_HEADER_KEY, accessToken)
            .build()
    }

    private fun Request.isRefreshRequest() = url.toString().endsWith(AuthApi.API_REFRESH_RELATIVE_PATH)

    /** Refresh token most likely expired. */
    private suspend fun reportUnauthorisedException() {
        sessionInteractor.reportUnauthorisedException()
    }

    /** Adding access token to the requests header, if there is an active user session. */
    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()
        sessionInteractor.sessionTokens?.let {
            val authHeader = request.headers[AuthApi.AUTH_TOKEN_HEADER_KEY]
            request = request.newBuilder().apply {
                if (authHeader == null) {
                    // Adding auth header only if it is missing:
                    // some request may provide the token header by themself (see refreshToken method from AuthAPI)
                    addHeader(AuthApi.AUTH_TOKEN_HEADER_KEY, it.accessToken)
                }
            }.build()
        }
        return chain.proceed(request)
    }
}