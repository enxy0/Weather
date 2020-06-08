package com.enxy.weather

import android.app.Application
import com.enxy.weather.di.appModule
import com.enxy.weather.di.networkModule
import com.enxy.weather.di.uiModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class AndroidApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@AndroidApplication)
            modules(appModule, networkModule, uiModule)
        }
    }
}