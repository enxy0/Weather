package com.enxy.weather.utils.extension

import com.enxy.weather.data.entity.FavouriteForecast
import com.enxy.weather.data.entity.Forecast
import com.enxy.weather.data.entity.Location

fun Forecast.toLocation() = Location(locationName, longitude, latitude)

fun Forecast.toFavouriteForecast() = FavouriteForecast(
    id = id,
    temperature = currentForecast.temperature,
    description = currentForecast.description,
    locationName = locationName,
    imageId = currentForecast.imageId
)