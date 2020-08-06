package com.enxy.weather.data.entity

import androidx.room.TypeConverters
import com.enxy.weather.data.db.Converters
import com.enxy.weather.data.entity.Units.Temperature

data class HourForecast(
    var currentForecastId: Int = 0,
    @TypeConverters(Converters::class)
    var temperature: Temperature,
    val time: String,
    val imageId: Int
)