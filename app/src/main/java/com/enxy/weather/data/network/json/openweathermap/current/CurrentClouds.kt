package com.enxy.weather.data.network.json.openweathermap.current

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class CurrentClouds(
    @SerializedName("all")
    @Expose
    val all: Int
)