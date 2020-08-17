package com.enxy.weather.utils

import com.enxy.weather.R
import com.enxy.weather.data.entity.*
import java.util.*

/**
 * [Forecast] object used for tests
 */
val fakeForecast = Forecast(
    id = -1,
    locationName = "Moscow, Central Administrative Okrug, Russia",
    longitude = 37.6174943,
    latitude = 55.7504461,
    timestamp = Calendar.getInstance(),
    wasOpenedLast = false,
    isFavourite = false,
    currentForecast = CurrentForecast(
        currentForecastId = 0,
        temperature = Temperature(21),
        description = "Broken clouds",
        feelsLike = Temperature(18),
        wind = Wind(5),
        pressure = Pressure(1022),
        humidity = 56,
        imageId = 2131230822
    ),
    hourForecastList = arrayListOf(
        HourForecast(0, Temperature(21), "15:00", 1),
        HourForecast(1, Temperature(19), "18:00", 2),
        HourForecast(2, Temperature(16), "21:00", 3),
        HourForecast(3, Temperature(15), "23:00", 4),
    ),
    dayForecastList = arrayListOf(
        DayForecast(Temperature(21), Temperature(16), "Thursday", "25.06", Wind(3), Pressure(5), 97, R.drawable.current_weather_thunderstorm_rain_heavy),
        DayForecast(Temperature(20), Temperature(15), "Friday", "26.06", Wind(3), Pressure(5), 97, R.drawable.current_weather_broken_clouds),
        DayForecast(Temperature(23), Temperature(17), "Saturday", "27.06", Wind(3), Pressure(5), 97, R.drawable.weather_clear_day),
        DayForecast(Temperature(17), Temperature(9), "Sunday", "28.06", Wind(3), Pressure(5), 97, R.drawable.current_weather_snow_middle),
        DayForecast(Temperature(10), Temperature(3), "Monday", "29.06", Wind(3), Pressure(5), 97, R.drawable.current_weather_mist),
        DayForecast(Temperature(22), Temperature(16), "Tuesday", "30.06", Wind(3), Pressure(5), 97, R.drawable.current_weather_scattered_clouds),
        DayForecast(Temperature(22), Temperature(17), "Wednesday", "01.07", Wind(3), Pressure(5), 97, R.drawable.current_weather_rain_light)
    )
)

/**
 * Moscow [Forecast] object used for tests
 */
val moscowForecast = fakeForecast.copy(
    id = 1,
    locationName = "Moscow, Central Administrative Okrug, Russia",
    longitude = 37.6174943,
    latitude = 55.7504461,
    wasOpenedLast = false,
    isFavourite = false
)

/**
 * Saint-Petersburg [Forecast] object used for tests
 */
val spbForecast = fakeForecast.copy(
    id = 2,
    locationName = "Saint-Petersburg, Russia",
    longitude = 30.3350986,
    latitude = 59.9342802,
    wasOpenedLast = true,
    isFavourite = true
)

/**
 * Paris [Forecast] object used for tests
 */
val parisForecast = fakeForecast.copy(
    id = 3,
    locationName = "Paris, France",
    longitude = 2.3522219,
    latitude = 48.856614,
    wasOpenedLast = false,
    isFavourite = true
)

/**
 * List of forecasts used for tests
 */
val forecasts = listOf(
    moscowForecast,
    spbForecast,
    parisForecast
)