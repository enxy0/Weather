package com.enxy.weather.data.network.json.openweathermap.current

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class CurrentCoordinates(
    @SerializedName("lon")
    @Expose
    val lon: Double,
    @SerializedName("lat")
    @Expose
    val lat: Double
)