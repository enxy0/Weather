package com.enxy.weather.di

import android.content.Context
import com.enxy.weather.AndroidApplication
import com.enxy.weather.data.AppDataBase
import com.enxy.weather.data.AppSettings
import com.enxy.weather.data.AppSettingsImpl
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class ApplicationModule(private val application: AndroidApplication) {
    @Provides
    @Singleton
    fun provideApplicationContext(): Context = application

    @Provides
    @Singleton
    fun provideDatabase(): AppDataBase = AppDataBase.getInstance(application)

    @Provides
    @Singleton
    fun providePreferences(): AppSettings = AppSettingsImpl(application)
}