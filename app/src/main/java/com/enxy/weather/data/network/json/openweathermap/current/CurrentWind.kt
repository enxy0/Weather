package com.enxy.weather.data.network.json.openweathermap.current


import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class CurrentWind(
    @SerializedName("speed")
    @Expose
    val speed: Double,
    @SerializedName("deg")
    @Expose
    val deg: Int
)