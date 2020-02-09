package com.enxy.weather.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters

@Entity(tableName = "hour_forecast")
data class HourForecast(
    @PrimaryKey(autoGenerate = true)
    var hourForecastId: Int = 0,
    @ColumnInfo(name = "hourLocationName")
    val locationName: String,
    @ColumnInfo(name = "hourLongitude")
    val longitude: Double,
    @ColumnInfo(name = "hourLatitude")
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