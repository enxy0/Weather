package com.enxy.weather.model

import com.google.gson.annotations.SerializedName

data class CurrentDataModel(
    @SerializedName("temperature")
    val temperature: Int,
    @SerializedName("wind")
    val wind: Int,
    @SerializedName("description")
    val description: String,
    @SerializedName("day")
    val day: String,
    @SerializedName("time")
    val time: String,
    @SerializedName("pressure")
    val pressure: Int,
    @SerializedName("humidity")
    val humidity: Int,
    @SerializedName("image")
    val image: String,
    @SerializedName("feels_like")
    val feels_like: Int
) {
    companion object {
        val EMPTY = "empty"
    }
}
