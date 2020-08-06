package com.enxy.weather.data.entity

import androidx.room.TypeConverters
import com.enxy.weather.data.db.Converters
import com.enxy.weather.data.entity.Units.Temperature

data class DayForecast(
    @TypeConverters(Converters::class)
    var highestTemp: Temperature,
    @TypeConverters(Converters::class)
    var lowestTemp: Temperature,
    val day: String,
    val date: String,
    val imageId: Int
)
