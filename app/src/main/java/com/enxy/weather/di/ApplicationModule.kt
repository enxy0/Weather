package com.enxy.weather.di

import android.content.Context
import com.enxy.weather.AndroidApplication
import com.enxy.weather.data.AppDataBase
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
}