package com.enxy.weather

import android.app.Application
import com.enxy.weather.data.AppSettings
import com.enxy.weather.di.appModule
import com.enxy.weather.di.networkModule
import com.enxy.weather.di.uiModule
import com.enxy.weather.utils.ThemeUtils
import org.koin.android.ext.android.inject
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class AndroidApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        // Set Up Koin DI
        startKoin {
            androidContext(this@AndroidApplication)
            modules(appModule, networkModule, uiModule)
        }

        // Set Up App Theme
        val settings by inject<AppSettings>()
        ThemeUtils.setAppTheme(settings.theme)
    }
}