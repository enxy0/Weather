package com.enxy.weather.ui.search

data class LocationInfo(
    val latitude: Double,
    val longitude: Double,
    val formattedLocationName: String,
    val confidence: Int
)
