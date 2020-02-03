package com.enxy.weather.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "current_forecast")
data class CurrentForecast(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var currentForecastId: Int = 0,
    val cityName: String,
    val longitude: Double,
    val latitude: Double,
    val temperature: String,
    val description: String,
    val feelsLikeTemperature: String,
    val wind: String,
    val pressure: String,
    val humidity: String,
    val imageId: Int
)
