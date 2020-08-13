package com.enxy.weather.data.entity

import androidx.annotation.DrawableRes
import com.enxy.weather.data.entity.Units.Temperature

data class MiniForecast (
    val id: Int,
    val temperature: Temperature,
    val description: String,
    val locationName: String,
    @DrawableRes val imageId: Int
//    val type: String
)