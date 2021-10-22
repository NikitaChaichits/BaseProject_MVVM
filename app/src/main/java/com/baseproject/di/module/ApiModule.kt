package com.baseproject.di.module

import com.baseproject.data.source.remote.api.AuthApi
import com.fahrer.data.source.remote.api.AuthApi
import com.fahrer.di.module.HttpModule

import dagger.Module
import dagger.Provides
import dagger.Reusable
import retrofit2.Retrofit

@Module(includes = [HttpModule::class])
object ApiModule {

    @Reusable
    @Provides
    fun provideAuthApiService(retrofit: Retrofit): AuthApi =
        retrofit.create(AuthApi::class.java)


}