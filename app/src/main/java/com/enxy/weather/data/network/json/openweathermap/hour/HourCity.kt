package com.enxy.weather.data.network.json.openweathermap.hour

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class HourCity(
    @SerializedName("id")
    @Expose
    val id: Int,
    @SerializedName("name")
    @Expose
    val name: String,
    @SerializedName("coord")
    @Expose
    val coord: HourCoordinates,
    @SerializedName("country")
    @Expose
    val country: String,
    @SerializedName("timezone")
    @Expose
    val timezone: Int
)