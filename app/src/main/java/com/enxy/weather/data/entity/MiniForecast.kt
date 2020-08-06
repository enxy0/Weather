package com.enxy.weather.data.entity

import com.enxy.weather.data.entity.Units.Temperature

data class MiniForecast (
    val id: Int,
    val temperature: Temperature,
    val description: String,
    val locationName: String
//    val type: String
)