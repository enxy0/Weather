package com.enxy.weather.model

import com.google.gson.annotations.SerializedName

data class DayWeatherModel(
    @SerializedName("day")
    val dayName: String,
    @SerializedName("temperatureHigh")
    val temperatureHigh: String,
    @SerializedName("temperatureLow")
    val temperatureLow: String,
    @SerializedName("date")
    val date: String,
    @SerializedName("imageId")
    val imageId: Int
)
