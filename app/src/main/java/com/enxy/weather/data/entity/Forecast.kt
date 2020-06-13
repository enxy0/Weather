package com.enxy.weather.data.entity

import androidx.room.*
import com.enxy.weather.data.Converters
import com.enxy.weather.utils.Pressure
import com.enxy.weather.utils.Temperature
import com.enxy.weather.utils.Wind
import java.util.*
import kotlin.math.roundToInt

@Entity(tableName = "forecast")
data class Forecast(
    @PrimaryKey
    val locationName: String,
    val longitude: Double,
    val latitude: Double,
    @TypeConverters(Converters::class)
    val timestamp: Calendar = Calendar.getInstance(),
    var wasOpenedLast: Boolean = true,
    var isFavourite: Boolean = false,
    @Embedded
    val currentForecast: CurrentForecast,
    @TypeConverters(Converters::class)
    val hourForecastList: ArrayList<HourForecast>
) {
    /** Checks if data is valid to display to the user.
     * Valid time: 2 hours (openweathermap for free forecast)
     * @see <a href="https://openweathermap.org/price">https://openweathermap.org/price</a>
     * @return Returns true if data is valid
     */
    fun isNotOutdated(): Boolean {
        val twoHoursInMillis = 2 * 60 * 60 * 1000
        val currentTimeInMillis = Calendar.getInstance().timeInMillis
        return currentTimeInMillis - timestamp.timeInMillis < twoHoursInMillis
    }

    /**
     * Applies units from the app settings to the forecast
     * Supported:
     * Temperature - celsius, fahrenheit
     * Wind - meters per second, kilometers per hour
     * Pressure - millimeters of mercury, hectoPascals
     * @return [Forecast] in new units of temperature, wind and pressure
     */
    fun inUnits(
        temperatureUnit: Temperature = Temperature.CELSIUS,
        windUnit: Wind = Wind.METERS_PER_SECOND,
        pressureUnit: Pressure = Pressure.MILLIMETERS_OF_MERCURY
    ): Forecast {
        // Functions to convert units
        var convertTemperature: (String) -> String = { it } // celsius used by default
        var convertWind: (String) -> String = { it } // mps used by default
        var convertPressure: (String) -> String = { it } // hPa used by default

        // Checking which units are used
        if (temperatureUnit == Temperature.FAHRENHEIT)
            convertTemperature = { celsiusToFahrenheit(it.toInt()).toString() }
        if (windUnit == Wind.KILOMETERS_PER_HOUR)
            convertWind = { metersPerSecToKilometersPerHour(it.toInt()).toString() }
        if (pressureUnit == Pressure.MILLIMETERS_OF_MERCURY)
            convertPressure = { hectoPascalsToMmHg(it.toInt()).toString() }

        // Applying units to the forecast
        this.currentForecast.apply {
            temperature = convertTemperature(temperature)
            feelsLike = convertTemperature(feelsLike)
            wind = convertWind(wind)
            pressure = convertPressure(pressure)
        }
        this.hourForecastList.map {
            it.apply { temperature = convertTemperature(temperature) }
        }
        return this
    }

    @Ignore
    private val millimeterOfMercury: Double = 133.3223684

    private fun hectoPascalsToMmHg(hectoPascals: Int): Int = (hectoPascals / millimeterOfMercury * 100).roundToInt()

    private fun celsiusToFahrenheit(celsius: Int): Int = (celsius * 1.8 + 32).roundToInt()

    private fun metersPerSecToKilometersPerHour(metersPerSec: Int): Int = metersPerSec * 60 * 60 / 1000
}