package com.enxy.weather.data

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
    val temperature: String,
    val description: String,
    val feelsLikeTemperature: String,
    val wind: String,
    val pressure: String,
    val humidity: String,
    val imageId: Int
)
