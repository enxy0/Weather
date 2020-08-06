package com.enxy.weather.data.entity

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.enxy.weather.data.db.Converters
import com.enxy.weather.utils.PressureUnit
import com.enxy.weather.utils.TemperatureUnit
import com.enxy.weather.utils.WindUnit
import java.util.*

@Entity(tableName = "forecast")
data class Forecast(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    var locationName: String,
    val longitude: Double,
    val latitude: Double,
    @TypeConverters(Converters::class)
    val timestamp: Calendar = Calendar.getInstance(),
    var wasOpenedLast: Boolean = true,
    var isFavourite: Boolean = false,
    @Embedded
    val currentForecast: CurrentForecast,
    @TypeConverters(Converters::class)
    val hourForecastList: ArrayList<HourForecast>,
    @TypeConverters(Converters::class)
    val dayForecastList: ArrayList<DayForecast>
) {
    /**
     * Checks if data is outdated or not.
     * Update interval: 2 hours (openweathermap for free forecast)
     * @see <a href="https://openweathermap.org/price">https://openweathermap.org/price</a>
     * @return Returns true if data is valid
     */
    fun isOutdated(): Boolean {
        val twoHoursInMillis = 2 * 60 * 60 * 1000 // 2 hours
        val currentTimeInMillis = Calendar.getInstance().timeInMillis
        return currentTimeInMillis - timestamp.timeInMillis >= twoHoursInMillis
    }

    /**
     * Updates all temperature units in [Forecast] according to new [unit]
     */
    fun updateTemperatureUnit(unit: TemperatureUnit) {
        currentForecast.temperature.updateUnit(unit)
        currentForecast.feelsLike.updateUnit(unit)
        hourForecastList.forEach {
            it.temperature.updateUnit(unit)
        }
        dayForecastList.forEach {
            it.highestTemp.updateUnit(unit)
            it.lowestTemp.updateUnit(unit)
        }
    }

    /**
     * Updates all pressure units in [Forecast] according to new [unit]
     */
    fun updatePressureUnit(unit: PressureUnit) {
        currentForecast.pressure.updateUnit(unit)
//        dayForecastList.forEach {
//            it.pressure.updateUnit(type)
//        }
    }

    /**
     * Updates all wind units in [Forecast] according to new [unit]
     */
    fun updateWindUnit(unit: WindUnit) {
        currentForecast.wind.updateUnit(unit)
//        dayForecastList.forEach {
//            it.wind.updateUnit(type)
//        }
    }
}