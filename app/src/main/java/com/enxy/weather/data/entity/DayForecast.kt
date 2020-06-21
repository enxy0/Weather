package com.enxy.weather.data.entity

data class DayForecast(
    var highestTemp: Int,
    var lowestTemp: Int,
    val day: String,
    val date: String,
    val imageId: Int
)
