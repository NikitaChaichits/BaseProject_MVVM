package com.fahrer.di.module

import com.fahrer.data.repository.AuthRepositoryImpl
import com.fahrer.data.repository.CommonRepositoryImpl
import com.fahrer.data.repository.FaqRepositoryImpl
import com.fahrer.data.repository.JobsRepositoryImpl
import com.fahrer.data.repository.NotificationsRepositoryImpl
import com.fahrer.data.repository.TransportRepositoryImpl
import com.fahrer.data.repository.UserRepositoryImpl
import com.fahrer.data.source.local.SharedPreferencesDataSource
import com.fahrer.data.source.local.db.dao.CommonsDao
import com.fahrer.data.source.local.db.dao.JobsDao
import com.fahrer.data.source.local.db.dao.NotificationsDao
import com.fahrer.data.source.local.db.dao.TransportDao
import com.fahrer.data.source.local.db.dao.UsersDao
import com.fahrer.data.source.remote.api.AuthApi
import com.fahrer.data.source.remote.api.CommonApi
import com.fahrer.data.source.remote.api.FaqApi
import com.fahrer.data.source.remote.api.JobsApi
import com.fahrer.data.source.remote.api.NotificationsApi
import com.fahrer.data.source.remote.api.TransportApi
import com.fahrer.data.source.remote.api.UserApi
import com.fahrer.domain.repository.AuthRepository
import com.fahrer.domain.repository.CommonRepository
import com.fahrer.domain.repository.FaqRepository
import com.fahrer.domain.repository.JobsRepository
import com.fahrer.domain.repository.NotificationsRepository
import com.fahrer.domain.repository.TransportRepository
import com.fahrer.domain.repository.UserRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
object RepositoriesModule {

    @Singleton
    @Provides
    fun provideUserRepository(
        api: UserApi,
        sharedPrefs: SharedPreferencesDataSource,
        dao: UsersDao
    ): UserRepository = UserRepositoryImpl(api, sharedPrefs, dao)

    @Singleton
    @Provides
    fun provideAuthRepository(
        api: AuthApi,
        sharedPrefs: SharedPreferencesDataSource
    ): AuthRepository = AuthRepositoryImpl(api, sharedPrefs)

    @Singleton
    @Provides
    fun provideNotificationsRepository(
        api: NotificationsApi,
        dao: NotificationsDao,
    ): NotificationsRepository = NotificationsRepositoryImpl(api, dao)

    @Singleton
    @Provides
    fun provideFaqRepository(
        api: FaqApi
    ): FaqRepository = FaqRepositoryImpl(api)

    @Singleton
    @Provides
    fun provideJobsRepository(
        api: JobsApi,
        dao: JobsDao,
        sharedPrefs: SharedPreferencesDataSource,
    ): JobsRepository = JobsRepositoryImpl(api, dao, sharedPrefs)

    @Singleton
    @Provides
    fun provideCommonRepository(
        api: CommonApi,
        dao: CommonsDao
    ): CommonRepository = CommonRepositoryImpl(api, dao)

    @Singleton
    @Provides
    fun provideTransportRepository(
        api: TransportApi,
        dao: TransportDao
    ): TransportRepository = TransportRepositoryImpl(api, dao)
}