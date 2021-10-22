package com.baseproject.di.components

import android.app.Application
import com.baseproject.ui.MainViewModel
import com.baseproject.ui.auth.AuthViewModel
import com.baseproject.ui.auth.login.LoginViewModel
import com.baseproject.ui.main.dashboard.DashboardViewModel
import com.baseproject.ui.main.notifications.NotificationsViewModel
import com.baseproject.ui.main.settings.SettingsViewModel
import com.fahrer.di.module.ApiModule
import com.fahrer.di.module.RepositoriesModule
import com.fahrer.di.module.RoomModule
import com.fahrer.di.module.WorkerModule
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [ApiModule::class, RepositoriesModule::class, RoomModule::class, WorkerModule::class])
interface AppComponent {

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance app: Application): AppComponent
    }

    val mainViewModel: MainViewModel

    /* Auth */

    val authViewModel: AuthViewModel
    val loginViewModel: LoginViewModel

    /* Main */

    val dashboardViewModel: DashboardViewModel
    val settingsViewModel: SettingsViewModel
    val notificationsViewModel: NotificationsViewModel
}
