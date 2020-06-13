package com.enxy.weather.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "current_forecast")
data class CurrentForecast(
    @PrimaryKey(autoGenerate = true)
    var currentForecastId: Int = 0,
    var temperature: String,
    val description: String,
    var feelsLike: String,
    var wind: String,
    var pressure: String,
    val humidity: String,
    val imageId: Int
)
