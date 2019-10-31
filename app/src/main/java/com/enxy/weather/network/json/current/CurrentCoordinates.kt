package com.enxy.weather.network.json.current

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