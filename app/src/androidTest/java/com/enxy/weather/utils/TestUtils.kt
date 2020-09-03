package com.enxy.weather.utils

import android.content.Context
import androidx.room.Room
import androidx.test.platform.app.InstrumentationRegistry
import com.enxy.weather.data.AppSettings
import com.enxy.weather.data.AppSettingsImpl
import com.enxy.weather.data.db.AppDataBase
import com.enxy.weather.data.location.LocationDataSource
import com.enxy.weather.data.location.LocationRepository
import com.enxy.weather.data.location.OpenCageDataSource
import com.enxy.weather.data.location.OpenCageRepository
import com.enxy.weather.data.network.LocationApi
import com.enxy.weather.data.network.WeatherApi
import com.enxy.weather.data.weather.OpenWeatherMapDataSource
import com.enxy.weather.data.weather.OpenWeatherMapRepository
import com.enxy.weather.data.weather.WeatherDataSource
import com.enxy.weather.ui.WeatherViewModel
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * [Context] object used for tests
 */
val context: Context
    get() = InstrumentationRegistry.getInstrumentation().targetContext

/**
 * [AppDataBase] object used for tests
 */
val appDatabase: AppDataBase
    get() = Room.inMemoryDatabaseBuilder(context, AppDataBase::class.java)
        .allowMainThreadQueries()
        .build()

/**
 * [WeatherApi] object used for tests
 */
val weatherApi: WeatherApi
    get() = Retrofit.Builder()
        .baseUrl(OPEN_WEATHER_MAP_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(WeatherApi::class.java)

/**
 * [LocationApi] object used for tests
 */
val locationApi: LocationApi
    get() = Retrofit.Builder()
        .baseUrl(OPEN_CAGE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(LocationApi::class.java)

/**
 * [OpenWeatherMapDataSource] object used for tests
 */
val weatherDataSource: WeatherDataSource
    get() = OpenWeatherMapDataSource(weatherApi)

/**
 * [OpenWeatherMapRepository] object used for tests
 */
val weatherRepository: OpenWeatherMapRepository
    get() = OpenWeatherMapRepository(weatherDataSource, appDatabase, appSettings)

val locationDataSource: LocationDataSource
    get() = OpenCageDataSource(locationApi)
/**
 * [LocationRepository] object used for tests
 */
val locationRepository: LocationRepository
    get() = OpenCageRepository(locationDataSource)

/**
 * [AppSettings] object used for tests
 */
val appSettings: AppSettings
    get() = AppSettingsImpl(context)

/**
 * [WeatherViewModel] object used for tests
 */
val weatherViewModel: WeatherViewModel
    get() = WeatherViewModel(weatherRepository, appSettings)