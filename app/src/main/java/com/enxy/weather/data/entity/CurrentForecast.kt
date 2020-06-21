package com.enxy.weather.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "current_forecast")
data class CurrentForecast(
    @PrimaryKey(autoGenerate = true)
    var currentForecastId: Int = 0,
    var temperature: Int,
    var feelsLike: Int,
    val description: String,
    var wind: Int,
    var pressure: Int,
    val humidity: Int,
    val imageId: Int
)
