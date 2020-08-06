package com.enxy.weather.utils.extension

import com.enxy.weather.data.entity.Forecast
import com.enxy.weather.data.entity.Location
import com.enxy.weather.data.entity.MiniForecast

fun Forecast.toLocation() = Location(locationName, longitude, latitude)

fun Forecast.toMiniForecast() = MiniForecast(
    id = id,
    temperature = currentForecast.temperature,
    description = currentForecast.description,
    locationName = locationName
)