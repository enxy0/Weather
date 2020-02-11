package com.enxy.weather.data.model

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.enxy.weather.data.Converters
import java.util.*

@Entity(tableName = "forecast")
data class Forecast(
    @PrimaryKey(autoGenerate = true)
    val forecastId: Int = 0,
    val locationName: String,
    val longitude: Double,
    val latitude: Double,
    @TypeConverters(Converters::class)
    val timestamp: Calendar = Calendar.getInstance(),
    val wasOpenedLast: Boolean = true,
    @Embedded
    val currentForecast: CurrentForecast,
    @Embedded
    val hourForecast: HourForecast
) {
    /** Checks if data is valid to display to the user.
     * Valid time: 2 hours (openweathermap for free forecast)
     * @see <a href="https://openweathermap.org/price">https://openweathermap.org/price</a>
     * @return Returns true if data is valid
     */
    fun containsValidInfo(): Boolean {
        val twoHoursInMillis = 2 * 60 * 60 * 1000
        val currentTimeInMillis = Calendar.getInstance().timeInMillis
        return currentTimeInMillis - timestamp.timeInMillis < twoHoursInMillis
    }
}