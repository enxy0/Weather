package com.enxy.weather.model

import com.google.gson.annotations.SerializedName

data class HourDataModel(
    @SerializedName("temperature")
    val temperature: String,
    @SerializedName("time")
    val time: String,
    @SerializedName("image")
    val image: String
) {
    companion object {
        val EMPTY = "empty"
    }
}
