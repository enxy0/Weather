package com.enxy.weather.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "current_weather")
data class CurrentForecast(
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
) {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var currentForecastId: Int = 0

    override fun toString(): String {
        return "CurrentForecast(currentForecastId=$currentForecastId, cityName=$cityName, " +
                "longitude=$longitude, latitude=$latitude, temperature=$temperature, " +
                "description=$description, feelsLikeTemperature=$feelsLikeTemperature, " +
                "wind=$wind, pressure=$pressure, humidity=$humidity, imageId=$imageId)"
    }
}
