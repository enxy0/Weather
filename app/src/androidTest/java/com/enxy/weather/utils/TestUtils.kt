package com.enxy.weather.utils

import android.content.Context
import androidx.room.Room
import androidx.test.platform.app.InstrumentationRegistry
import com.enxy.weather.R
import com.enxy.weather.data.AppSettings
import com.enxy.weather.data.AppSettingsImpl
import com.enxy.weather.data.db.AppDataBase
import com.enxy.weather.data.entity.CurrentForecast
import com.enxy.weather.data.entity.DayForecast
import com.enxy.weather.data.entity.Forecast
import com.enxy.weather.data.entity.HourForecast
import com.enxy.weather.data.network.LocationApi
import com.enxy.weather.data.network.WeatherApi
import com.enxy.weather.data.repository.LocationRepository
import com.enxy.weather.data.repository.WeatherRepository
import com.enxy.weather.ui.WeatherViewModel
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*

val context: Context
    get() = InstrumentationRegistry.getInstrumentation().targetContext

val appDatabase: AppDataBase
    get() = Room.inMemoryDatabaseBuilder(context, AppDataBase::class.java)
        .allowMainThreadQueries()
        .build()

val weatherApi: WeatherApi
    get() = Retrofit.Builder()
        .baseUrl(OPEN_WEATHER_MAP_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(WeatherApi::class.java)

val locationApi: LocationApi
    get() = Retrofit.Builder()
        .baseUrl(OPEN_CAGE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(LocationApi::class.java)

val weatherRepository: WeatherRepository
    get() = WeatherRepository(weatherApi, appDatabase)

val locationRepository: LocationRepository
    get() = LocationRepository(locationApi)

val appSettings: AppSettings
    get() = AppSettingsImpl(context)

val weatherViewModel: WeatherViewModel
    get() = WeatherViewModel(weatherRepository, locationRepository, appSettings)

val moscowForecast = Forecast(
    locationName = "Moscow, Central Administrative Okrug, Russia",
    longitude = 37.6174943,
    latitude = 55.7504461,
    timestamp = Calendar.getInstance(),
    wasOpenedLast = false,
    isFavourite = false,
    currentForecast = CurrentForecast(
        currentForecastId = 0,
        temperature = 21,
        description = "Broken clouds",
        feelsLike = 18,
        wind = 5,
        pressure = 1022,
        humidity = 56,
        imageId = 2131230822
    ),
    hourForecastList = arrayListOf(
        HourForecast(0, 21, "15:00", 1),
        HourForecast(1, 19, "18:00", 2),
        HourForecast(2, 16, "21:00", 3),
        HourForecast(3, 15, "23:00", 4)
    ),
    dayForecastList = arrayListOf(
        DayForecast(21, 16, "Thursday", "25.06", R.drawable.current_weather_thunderstorm_rain_heavy),
        DayForecast(20, 15, "Friday", "26.06", R.drawable.current_weather_broken_clouds),
        DayForecast(23, 17, "Saturday", "27.06", R.drawable.weather_clear_day),
        DayForecast(17, 9, "Sunday", "28.06", R.drawable.current_weather_snow_middle),
        DayForecast(10, 3, "Monday", "29.06", R.drawable.current_weather_mist),
        DayForecast(22, 16, "Tuesday", "30.06", R.drawable.current_weather_scattered_clouds),
        DayForecast(22, 17, "Wednesday", "01.07", R.drawable.current_weather_rain_light)
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
        temperature = +21,
        description = "Broken clouds",
        feelsLike = 18,
        wind = 5,
        pressure = 1022,
        humidity = 56,
        imageId = 2131230822
    ),
    hourForecastList = arrayListOf(
        HourForecast(0, 21, "15:00", 1),
        HourForecast(1, 19, "18:00", 2),
        HourForecast(2, 16, "21:00", 3),
        HourForecast(3, 15, "23:00", 4)
    ),
    dayForecastList = arrayListOf(
        DayForecast(21, 16, "Thursday", "25.06", R.drawable.current_weather_thunderstorm_rain_heavy),
        DayForecast(20, 15, "Friday", "26.06", R.drawable.current_weather_broken_clouds),
        DayForecast(23, 17, "Saturday", "27.06", R.drawable.weather_clear_day),
        DayForecast(17, 9, "Sunday", "28.06", R.drawable.current_weather_snow_middle),
        DayForecast(10, 3, "Monday", "29.06", R.drawable.current_weather_mist),
        DayForecast(22, 16, "Tuesday", "30.06", R.drawable.current_weather_scattered_clouds),
        DayForecast(22, 17, "Wednesday", "01.07", R.drawable.current_weather_rain_light)
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
        temperature = +21,
        description = "Broken clouds",
        feelsLike = 18,
        wind = 5,
        pressure = 1022,
        humidity = 56,
        imageId = 2131230822
    ),
    hourForecastList = arrayListOf(
        HourForecast(0, 21, "15:00", 1),
        HourForecast(1, 19, "18:00", 2),
        HourForecast(2, 16, "21:00", 3),
        HourForecast(3, 15, "23:00", 4)
    ),
    dayForecastList = arrayListOf(
        DayForecast(21, 16, "Thursday", "25.06", R.drawable.current_weather_thunderstorm_rain_heavy),
        DayForecast(20, 15, "Friday", "26.06", R.drawable.current_weather_broken_clouds),
        DayForecast(23, 17, "Saturday", "27.06", R.drawable.weather_clear_day),
        DayForecast(17, 9, "Sunday", "28.06", R.drawable.current_weather_snow_middle),
        DayForecast(10, 3, "Monday", "29.06", R.drawable.current_weather_mist),
        DayForecast(22, 16, "Tuesday", "30.06", R.drawable.current_weather_scattered_clouds),
        DayForecast(22, 17, "Wednesday", "01.07", R.drawable.current_weather_rain_light)
    )
)

val forecasts = listOf(
    moscowForecast,
    spbForecast,
    parisForecast
)