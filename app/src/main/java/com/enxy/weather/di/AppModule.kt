package com.enxy.weather.di

import androidx.room.Room
import com.enxy.weather.data.AppSettings
import com.enxy.weather.data.AppSettingsImpl
import com.enxy.weather.data.db.AppDataBase
import com.enxy.weather.data.db.AppDataBase.Companion.DATABASE_NAME
import com.enxy.weather.data.location.LocationDataSource
import com.enxy.weather.data.location.LocationRepository
import com.enxy.weather.data.location.OpenCageDataSource
import com.enxy.weather.data.location.OpenCageRepository
import com.enxy.weather.data.weather.OpenWeatherMapDataSource
import com.enxy.weather.data.weather.OpenWeatherMapRepository
import com.enxy.weather.data.weather.WeatherDataSource
import com.enxy.weather.data.weather.WeatherRepository
import com.enxy.weather.ui.WeatherViewModel
import com.enxy.weather.ui.favourite.FavouriteViewModel
import com.enxy.weather.ui.search.SearchViewModel
import com.enxy.weather.ui.settings.SettingsViewModel
import org.koin.android.ext.koin.androidApplication
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    viewModel { WeatherViewModel(get(), get()) }
    viewModel { SettingsViewModel(get()) }
    viewModel { FavouriteViewModel(get()) }
    viewModel { SearchViewModel(get()) }
    single<LocationDataSource> { OpenCageDataSource(get()) }
    single<LocationRepository> { OpenCageRepository(get()) }
    single<WeatherDataSource> { OpenWeatherMapDataSource(get()) }
    single<WeatherRepository> { OpenWeatherMapRepository(get(), get(), get()) }
    single<AppSettings> { AppSettingsImpl(androidApplication()) }
    single {
        Room.databaseBuilder(androidApplication(), AppDataBase::class.java, DATABASE_NAME).build()
    }
}