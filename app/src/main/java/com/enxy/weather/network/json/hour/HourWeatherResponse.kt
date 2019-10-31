package com.enxy.weather.network.json.hour

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


data class HourWeatherResponse(
    @SerializedName("cod")
    @Expose
    val cod: String,
    @SerializedName("message")
    @Expose
    val message: Double,
    @SerializedName("cnt")
    @Expose
    val cnt: Int,
    @SerializedName("list")
    @Expose
    val list: List<HourList>,
    @SerializedName("city")
    @Expose
    val city: HourCity
)