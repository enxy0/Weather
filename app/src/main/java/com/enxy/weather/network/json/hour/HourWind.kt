package com.enxy.weather.network.json.hour


import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class HourWind(
    @SerializedName("speed")
    @Expose
    val speed: Double,
    @SerializedName("deg")
    @Expose
    val deg: Double
)