package com.enxy.weather.data.network.json.openweathermap.hour

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


data class HourList(
    @SerializedName("dt")
    @Expose
    val dt: Long,
    @SerializedName("main")
    @Expose
    val main: HourMain,
    @SerializedName("weather")
    @Expose
    val weather: List<HourWeather>,
    @SerializedName("clouds")
    @Expose
    val clouds: HourClouds,
    @SerializedName("wind")
    @Expose
    val wind: HourWind,
    @SerializedName("sys")
    @Expose
    val sys: HourSys,
    @SerializedName("dt_txt")
    @Expose
    val dtTxt: String,
    @SerializedName("rain")
    @Expose
    val rain: HourRain
)