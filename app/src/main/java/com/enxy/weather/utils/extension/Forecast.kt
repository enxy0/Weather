package com.enxy.weather.utils.extension

import com.enxy.weather.data.entity.Forecast
import com.enxy.weather.data.entity.Location

fun Forecast.toLocation() = Location(locationName, longitude, latitude)