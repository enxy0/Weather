package com.enxy.weather.utils

import android.content.Context
import androidx.room.Room
import androidx.test.platform.app.InstrumentationRegistry
import com.enxy.weather.data.AppSettings
import com.enxy.weather.data.AppSettingsImpl
import com.enxy.weather.data.db.AppDataBase
import com.enxy.weather.data.entity.CurrentForecast
import com.enxy.weather.data.entity.Forecast
import com.enxy.weather.data.entity.HourForecast
import com.enxy.weather.data.network.NetworkService
import com.enxy.weather.data.repository.LocationRepository
import com.enxy.weather.data.repository.WeatherRepository
import com.enxy.weather.ui.MainViewModel
import java.util.*

val networkService: NetworkService
    get() = NetworkService()

val context: Context
    get() = InstrumentationRegistry.getInstrumentation().targetContext

val appDatabase: AppDataBase
    get() = Room.inMemoryDatabaseBuilder(context, AppDataBase::class.java)
        .allowMainThreadQueries()
        .build()
val weatherRepository: WeatherRepository
    get() = WeatherRepository(networkService, appDatabase)

val appSettings: AppSettings
    get() = AppSettingsImpl(context)

val locationRepository: LocationRepository
    get() = LocationRepository(networkService)

val mainViewModel: MainViewModel
    get() = MainViewModel(weatherRepository, locationRepository, appSettings)

val moscowForecast = Forecast(
    locationName = "Moscow, Central Administrative Okrug, Russia",
    longitude = 37.6174943,
    latitude = 55.7504461,
    timestamp = Calendar.getInstance(),
    wasOpenedLast = false,
    isFavourite = false,
    currentForecast = CurrentForecast(
        currentForecastId = 0,
        temperature = "+21",
        description = "Broken clouds",
        feelsLike = "18",
        wind = "5",
        pressure = "1022",
        humidity = "56",
        imageId = 2131230822
    ),
    hourForecastList = arrayListOf(
        HourForecast(0, "+21", "15:00", 1),
        HourForecast(1, "+19", "18:00", 2),
        HourForecast(2, "+16", "21:00", 3),
        HourForecast(3, "+15", "23:00", 4)
    )
)

val spbForecast = Forecast(
    locationName = "Saint-Petersburg, Russia",
    longitude = 30.3350986,
    latitude = 59.9342802,
    timestamp = Calendar.getInstance(),
    wasOpenedLast = true,
    isFavourite = true,
    currentForecast = CurrentForecast(
        currentForecastId = 0,
        temperature = "+21",
        description = "Broken clouds",
        feelsLike = "18",
        wind = "5",
        pressure = "1022",
        humidity = "56",
        imageId = 2131230822
    ),
    hourForecastList = arrayListOf(
        HourForecast(0, "+21", "15:00", 1),
        HourForecast(1, "+19", "18:00", 2),
        HourForecast(2, "+16", "21:00", 3),
        HourForecast(3, "+15", "23:00", 4)
    )
)

val parisForecast = Forecast(
    locationName = "Paris, France",
    longitude = 2.3522219,
    latitude = 48.856614,
    timestamp = Calendar.getInstance(),
    wasOpenedLast = false,
    isFavourite = true,
    currentForecast = CurrentForecast(
        currentForecastId = 0,
        temperature = "+21",
        description = "Broken clouds",
        feelsLike = "18",
        wind = "5",
        pressure = "1022",
        humidity = "56",
        imageId = 2131230822
    ),
    hourForecastList = arrayListOf(
        HourForecast(0, "+21", "15:00", 1),
        HourForecast(1, "+19", "18:00", 2),
        HourForecast(2, "+16", "21:00", 3),
        HourForecast(3, "+15", "23:00", 4)
    )
)

val forecasts = listOf(
    moscowForecast,
    spbForecast,
    parisForecast
)