package com.fahrer.di.module

import android.util.Log
import androidx.work.Configuration
import com.fahrer.util.worker.AppWorkerFactory
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class WorkerModule {

    @Singleton
    @Provides
    fun provideWorkManagerConfiguration(appWorkerFactory: AppWorkerFactory): Configuration =
        Configuration.Builder()
            .setMinimumLoggingLevel(Log.INFO)
            .setWorkerFactory(appWorkerFactory)
            .build()

}