package com.fahrer.di.module

import com.fahrer.common.constant.TIME_OUT
import com.fahrer.BuildConfig
import com.fahrer.data.mapper.typeadapter.TypeAdaptersSupplier
import com.fahrer.data.source.remote.api.adapter.ResultAdapterFactory
import com.fahrer.data.source.remote.api.auth.AuthManager
import com.fahrer.data.source.remote.api.mock.MockInterceptor
import com.fahrer.data.source.remote.api.subdomaininterceptor.SubdomainInterceptor
import com.fahrer.data.util.ApiErrorMapper
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.logging.HttpLoggingInterceptor.Level.BODY
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
object HttpModule {

    @Provides
    fun provideOkHttp(
        mockInterceptor: MockInterceptor,
        authManager: AuthManager,
        subdomainInterceptor: SubdomainInterceptor
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .apply {
                connectTimeout(TIME_OUT, TimeUnit.SECONDS)
                readTimeout(TIME_OUT, TimeUnit.SECONDS)
                writeTimeout(TIME_OUT, TimeUnit.SECONDS)
                addInterceptor(subdomainInterceptor)
                addInterceptor(authManager)
                authenticator(authManager)
                if (BuildConfig.DEBUG) addInterceptor(HttpLoggingInterceptor().setLevel(BODY))
                if (BuildConfig.USE_MOCKED_DATA) addInterceptor(mockInterceptor)
            }
            .build()
    }

    @Provides
    fun provideGson(typeAdaptersSupplier: TypeAdaptersSupplier): Gson {
        return GsonBuilder().apply {
            typeAdaptersSupplier.typeAdaptersMap.entries.forEach {
                registerTypeAdapter(it.key, it.value)
            }
        }.create()
    }

    @Singleton
    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient, gson: Gson): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .addCallAdapterFactory(ResultAdapterFactory(ApiErrorMapper(gson)))
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(okHttpClient)
            .build()
    }
}