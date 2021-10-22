package com.fahrer.di.module

import android.app.Application
import androidx.room.Room
import com.fahrer.data.source.local.db.AppDatabase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RoomModule {
    @Singleton
    @Provides
    fun provideAppDatabase(application: Application): AppDatabase =
        Room.databaseBuilder(application, AppDatabase::class.java, AppDatabase.DB_NAME)
            .build()

    @Provides
    fun provideJobsDao(database: AppDatabase) = database.jobsDao

    @Provides
    fun provideTransportDao(database: AppDatabase) = database.transportDao

    @Provides
    fun provideUsersDao(database: AppDatabase) = database.usersDao

    @Provides
    fun provideCommonsDao(database: AppDatabase) = database.commonsDao

    @Provides
    fun provideNotificationsDao(database: AppDatabase) = database.notificationDao
}