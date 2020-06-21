package com.enxy.weather.data.entity

data class HourForecast(
    var currentForecastId: Int = 0,
    var temperature: Int,
    val time: String,
    val imageId: Int
)