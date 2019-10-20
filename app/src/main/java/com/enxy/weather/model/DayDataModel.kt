package com.enxy.weather.model

import com.google.gson.annotations.SerializedName

data class DayDataModel(
    @SerializedName("day")
    val day: String,
    @SerializedName("temperatureHigh")
    val temperatureHigh: String,
    @SerializedName("temperatureLow")
    val temperatureLow: String,
    @SerializedName("date")
    val date: String,
    @SerializedName("image")
    val image: String
) {
   companion object {
       val EMPTY = "empty"
   }
}
