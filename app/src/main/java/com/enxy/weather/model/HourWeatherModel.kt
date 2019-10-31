package com.enxy.weather.model

import com.google.gson.annotations.SerializedName

data class HourWeatherModel(
    @SerializedName("temperature")
    val temperature: String,
    @SerializedName("time")
    val time: String,
    @SerializedName("image_id")
    val imageId: Int
)
