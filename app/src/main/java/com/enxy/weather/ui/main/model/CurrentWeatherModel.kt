package com.enxy.weather.ui.main.model

import com.google.gson.annotations.SerializedName

data class CurrentWeatherModel(
    @SerializedName("temperature")
    val temperature: String,
    @SerializedName("description")
    val description: String,
    @SerializedName("feels_like")
    val feelsLikeTemperature: String,
    @SerializedName("wind")
    val wind: String,
    @SerializedName("pressure")
    val pressure: String,
    @SerializedName("humidity")
    val humidity: String,
    @SerializedName("image_id")
    val imageId: Int,
    @SerializedName("city_name")
    val cityName: String
)
