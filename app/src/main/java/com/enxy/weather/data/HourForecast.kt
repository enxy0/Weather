package com.enxy.weather.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters

@Entity(tableName = "hour_forecast")
data class HourForecast(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var hourForecastId: Int = 0,
    val cityName: String,
    val longitude: Double,
    val latitude: Double,
    @TypeConverters(Converters::class)
    val hourArrayList: ArrayList<Hour>
)

@Entity(tableName = "hour")
data class Hour(
    val temperature: String,
    val time: String,
    val imageId: Int
)