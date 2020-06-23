package com.enxy.weather.data.entity

import androidx.room.*
import com.enxy.weather.data.db.Converters
import com.enxy.weather.utils.Pressure
import com.enxy.weather.utils.Temperature
import com.enxy.weather.utils.Wind
import java.util.*
import kotlin.math.roundToInt

@Entity(tableName = "forecast")
data class Forecast(
    @PrimaryKey
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
     * Applies given units to the forecast
     *
     * Supported:
     * Temperature - celcius (default), fahrenheit
     * Wind - meters per second (default), kilometers per hour
     * Pressure - hectoPascals (default), millimeters of mercury
     * @return [Forecast] in new units of temperature, wind and pressure
     */
    fun inUnits(
        temperatureUnit: Temperature = Temperature.CELSIUS,
        windUnit: Wind = Wind.METERS_PER_SECOND,
        pressureUnit: Pressure = Pressure.MILLIMETERS_OF_MERCURY
    ): Forecast = copy(
        currentForecast = currentForecast.copy(
            temperature = when (temperatureUnit) {
                Temperature.FAHRENHEIT -> celsiusToFahrenheit(currentForecast.temperature)
                else -> currentForecast.temperature
            },
            feelsLike = when (temperatureUnit) {
                Temperature.FAHRENHEIT -> celsiusToFahrenheit(currentForecast.feelsLike)
                else -> currentForecast.feelsLike
            },
            wind = when (windUnit) {
                Wind.KILOMETERS_PER_HOUR -> metersPerSecToKilometersPerHour(currentForecast.wind)
                else -> currentForecast.wind
            },
            pressure = when (pressureUnit) {
                Pressure.MILLIMETERS_OF_MERCURY -> hectoPascalsToMmHg(currentForecast.pressure)
                else -> currentForecast.pressure
            }
        ),
        hourForecastList = hourForecastList.map { hourForecast ->
            hourForecast.temperature = when (temperatureUnit) {
                Temperature.FAHRENHEIT -> celsiusToFahrenheit(hourForecast.temperature)
                else -> hourForecast.temperature
            }
            hourForecast
        } as ArrayList,
        dayForecastList = dayForecastList.map { dayForecast ->
            if (temperatureUnit == Temperature.FAHRENHEIT) {
                dayForecast.highestTemp = celsiusToFahrenheit(dayForecast.highestTemp)
                dayForecast.lowestTemp = celsiusToFahrenheit(dayForecast.lowestTemp)
            }
            dayForecast
        } as ArrayList
    )

    @Ignore private val millimeterOfMercury: Double = 133.3223684

    private fun hectoPascalsToMmHg(hectoPascals: Int): Int =
        (hectoPascals / millimeterOfMercury * 100).roundToInt()

    private fun celsiusToFahrenheit(celsius: Int): Int = (celsius * 1.8 + 32).roundToInt()

    private fun metersPerSecToKilometersPerHour(metersPerSec: Int): Int =
        metersPerSec * 60 * 60 / 1000
}