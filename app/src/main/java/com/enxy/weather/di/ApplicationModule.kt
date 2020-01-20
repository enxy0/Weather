package com.enxy.weather.di

import android.content.Context
import com.enxy.weather.AndroidApplication
import com.enxy.weather.network.NetworkService
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class ApplicationModule(private val application: AndroidApplication) {
    @Provides @Singleton fun provideApplicationContext(): Context = application

    @Provides @Singleton fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(NetworkService.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}