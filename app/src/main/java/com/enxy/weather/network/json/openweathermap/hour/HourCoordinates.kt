package com.enxy.weather.network.json.openweathermap.hour

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class HourCoordinates(
    @SerializedName("lat")
    @Expose
    val lat: Double,
    @SerializedName("lon")
    @Expose
    val lon: Double
)