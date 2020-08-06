package com.enxy.weather.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.enxy.weather.data.db.Converters
import com.enxy.weather.data.entity.Units.*

@Entity(tableName = "current_forecast")
data class CurrentForecast(
    @PrimaryKey(autoGenerate = true)
    var currentForecastId: Int = 0,
    @TypeConverters(Converters::class)
    var temperature: Temperature,
    @TypeConverters(Converters::class)
    var feelsLike: Temperature,
    val description: String,
    @TypeConverters(Converters::class)
    var wind: Wind,
    @TypeConverters(Converters::class)
    var pressure: Pressure,
    val humidity: Int,
    val imageId: Int
)
