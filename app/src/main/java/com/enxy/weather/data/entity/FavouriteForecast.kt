package com.enxy.weather.data.entity

import androidx.annotation.DrawableRes

data class FavouriteForecast (
    val id: Int,
    val temperature: Temperature,
    val description: String,
    val locationName: String,
    @DrawableRes val imageId: Int
)