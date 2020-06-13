package com.enxy.weather.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "hour")
data class HourForecast(
    @PrimaryKey(autoGenerate = true)
    var currentForecastId: Int = 0,
    var temperature: String,
    val time: String,
    val imageId: Int
)