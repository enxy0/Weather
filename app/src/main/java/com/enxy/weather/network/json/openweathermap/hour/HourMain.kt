package com.enxy.weather.network.json.openweathermap.hour

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


class HourMain(
    @SerializedName("temp")
    @Expose
    val temp: Double,
    @SerializedName("temp_min")
    @Expose
    val tempMin: Double,
    @SerializedName("temp_max")
    @Expose
    val tempMax: Double,
    @SerializedName("pressure")
    @Expose
    val pressure: Double,
    @SerializedName("sea_level")
    @Expose
    val seaLevel: Double,
    @SerializedName("grnd_level")
    @Expose
    val grndLevel: Double,
    @SerializedName("humidity")
    @Expose
    val humidity: Int
)