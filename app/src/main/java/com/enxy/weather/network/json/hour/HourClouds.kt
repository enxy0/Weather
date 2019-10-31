package com.enxy.weather.network.json.hour

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class HourClouds(
    @SerializedName("all")
    @Expose
    val all: Int
)