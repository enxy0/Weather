package com.enxy.weather.ui.search

data class LocationInfo(
    val longitude: Double,
    val latitude: Double,
    val formattedLocationName: String? = null,
    val confidence: Int? = null
)
