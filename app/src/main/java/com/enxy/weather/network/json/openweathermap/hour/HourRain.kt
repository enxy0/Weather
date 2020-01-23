package com.enxy.weather.network.json.openweathermap.hour

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class HourRain(
    @SerializedName("3h")
    @Expose
    val _3h: Double
)