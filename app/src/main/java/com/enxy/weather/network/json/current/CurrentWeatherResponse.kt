package com.enxy.weather.network.json.current

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


data class CurrentWeatherResponse(
    @SerializedName("coord")
    @Expose
    val coord: CurrentCoordinates,
    @SerializedName("weather")
    @Expose
    val weather: List<CurrentWeather>,
    @SerializedName("base")
    @Expose
    val base: String,
    @SerializedName("main")
    @Expose
    val main: CurrentMain,
    @SerializedName("visibility")
    @Expose
    val visibility: Int,
    @SerializedName("wind")
    @Expose
    val wind: CurrentWind,
    @SerializedName("clouds")
    @Expose
    val clouds: CurrentClouds,
    @SerializedName("dt")
    @Expose
    val dt: Int,
    @SerializedName("sys")
    @Expose
    val sys: CurrentSys,
    @SerializedName("id")
    @Expose
    val id: Int,
    @SerializedName("name")
    @Expose
    val name: String,
    @SerializedName("cod")
    @Expose
    val cod: Int
)