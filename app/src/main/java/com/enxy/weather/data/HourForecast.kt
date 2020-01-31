package com.enxy.weather.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters

@Entity(tableName = "hour_weather")
data class HourForecast(
    val cityName: String,
    val longitude: Double,
    val latitude: Double,
    @TypeConverters(HourListConverter::class)
    val hourArrayList: ArrayList<Hour>
) {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var hourForecastId: Int = 0

    override fun toString(): String {
        return "HourForecast(hourForecastId=$hourForecastId, cityName=$cityName, " +
                "longitude=$longitude, latitude=$latitude, hourArrayList=$hourArrayList"
    }
}

@Entity(tableName = "hour")
data class Hour(
    val temperature: String,
    val time: String,
    val imageId: Int
)