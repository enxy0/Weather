package com.enxy.weather.di

import com.enxy.weather.data.AppSettings
import com.enxy.weather.data.AppSettingsImpl
import com.enxy.weather.data.db.AppDataBase
import com.enxy.weather.data.repository.LocationRepository
import com.enxy.weather.data.repository.WeatherRepository
import com.enxy.weather.ui.WeatherViewModel
import com.enxy.weather.ui.settings.SettingsViewModel
import org.koin.android.ext.koin.androidApplication
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    viewModel { WeatherViewModel(get(), get(), get()) }
    viewModel { SettingsViewModel(get()) }
    single { WeatherRepository(get(), get()) }
    single { LocationRepository(get()) }
    single { AppDataBase.getInstance(androidApplication()) }
    single { AppSettingsImpl(androidApplication()) as AppSettings }
}