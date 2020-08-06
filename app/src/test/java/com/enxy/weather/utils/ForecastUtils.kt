package com.enxy.weather.utils

import com.enxy.weather.R
import com.enxy.weather.data.entity.*
import java.util.*


/**
 * [Forecast] object used for tests
 */
val forecast: Forecast
    get() = Forecast(
        locationName = "Moscow, Central Administrative Okrug, Russia",
        longitude = 37.6174943,
        latitude = 55.7504461,
        timestamp = Calendar.getInstance(),
        wasOpenedLast = false,
        isFavourite = false,
        currentForecast = CurrentForecast(
            currentForecastId = 0,
            temperature = Units.Temperature(21),
            description = "Broken clouds",
            feelsLike = Units.Temperature(18),
            wind = Units.Wind(5),
            pressure = Units.Pressure(1022),
            humidity = 56,
            imageId = 2131230822
        ),
        hourForecastList = arrayListOf(
            HourForecast(0, Units.Temperature(21), "15:00", 1),
            HourForecast(1, Units.Temperature(19), "18:00", 2),
            HourForecast(2, Units.Temperature(16), "21:00", 3),
            HourForecast(3, Units.Temperature(15), "23:00", 4)
        ),
        dayForecastList = arrayListOf(
            DayForecast(
                highestTemp = Units.Temperature(21),
                lowestTemp = Units.Temperature(19),
                day = "Thursday",
                date = "25.06",
                imageId = R.drawable.current_weather_rain_light
            )
        )
    )