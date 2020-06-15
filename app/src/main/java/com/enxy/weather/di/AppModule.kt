package com.enxy.weather.di

import com.enxy.weather.data.AppSettingsImpl
import com.enxy.weather.data.db.AppDataBase
import com.enxy.weather.data.repository.LocationRepository
import com.enxy.weather.data.repository.WeatherRepository
import com.enxy.weather.ui.MainViewModel
import com.enxy.weather.ui.settings.SettingsViewModel
import org.koin.android.ext.koin.androidApplication
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    viewModel { MainViewModel(get(), get(), get()) }
    viewModel { SettingsViewModel(get()) }
    factory { WeatherRepository(get(), get()) }
    factory { LocationRepository(get()) }
    single { AppDataBase.getInstance(androidApplication()) }
    single { AppSettingsImpl(androidApplication()) }
}