package com.enxy.weather.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "current_forecast")
data class CurrentForecast(
    @PrimaryKey(autoGenerate = true)
    var currentForecastId: Int = 0,
    @ColumnInfo(name = "currentLocationName")
    val locationName: String,
    @ColumnInfo(name = "currentLongitude")
    val longitude: Double,
    @ColumnInfo(name = "currentLatitude")
    val latitude: Double,
    var temperature: String,
    val description: String,
    var feelsLikeTemperature: String,
    var wind: String,
    var pressure: String,
    val humidity: String,
    val imageId: Int
)
