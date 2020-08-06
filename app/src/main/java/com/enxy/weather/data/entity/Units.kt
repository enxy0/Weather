package com.enxy.weather.data.entity

import com.enxy.weather.utils.PressureUnit
import com.enxy.weather.utils.PressureUnit.HECTO_PASCALS
import com.enxy.weather.utils.PressureUnit.MILLIMETERS_OF_MERCURY
import com.enxy.weather.utils.TemperatureUnit
import com.enxy.weather.utils.TemperatureUnit.CELSIUS
import com.enxy.weather.utils.TemperatureUnit.FAHRENHEIT
import com.enxy.weather.utils.WindUnit
import com.enxy.weather.utils.WindUnit.KILOMETERS_PER_HOUR
import com.enxy.weather.utils.WindUnit.METERS_PER_SECOND
import kotlin.math.roundToInt


/**
 * @param raw holds value in default units (temp - celsius, wind - m/s, pressure - hPa)
 */
sealed class Units(val raw: Int) {
    /**
     * Holds converted value in given units
     */
   abstract var value: Int

    class Temperature(raw: Int, private var type: TemperatureUnit = CELSIUS) : Units(raw) {

        override var value: Int = raw

        fun updateUnit(type: TemperatureUnit) {
            this.type = type
            value = when (type) {
                CELSIUS -> raw
                FAHRENHEIT -> celsiusToFahrenheit(raw)
            }
        }

        /**
         * Converts celsius to fahrenheit
         */
        private fun celsiusToFahrenheit(celsius: Int): Int = (celsius * 1.8 + 32).roundToInt()
    }

    class Wind(raw: Int, private var type: WindUnit = METERS_PER_SECOND) : Units(raw) {

        override var value: Int = raw

        fun updateUnit(type: WindUnit) {
            this.type = type
            value = when (type) {
                METERS_PER_SECOND -> raw
                KILOMETERS_PER_HOUR -> metersPerSecToKilometersPerHour(raw)
            }
        }

        /**
         * Converts m/s into km/h
         */
        private fun metersPerSecToKilometersPerHour(metersPerSec: Int): Int =
            metersPerSec * 60 * 60 / 1000
    }

    class Pressure(raw: Int, private var type: PressureUnit = HECTO_PASCALS) : Units(raw) {

        override var value: Int = raw

        fun updateUnit(type: PressureUnit) {
            this.type = type
            value = when (type) {
                HECTO_PASCALS -> raw
                MILLIMETERS_OF_MERCURY -> hectoPascalsToMmHg(raw)
            }
        }


        /**
         * Converts hPa into mmHg
         */
        private fun hectoPascalsToMmHg(hectoPascals: Int): Int {
            val millimeterOfMercury = 133.3223684
            return (hectoPascals / millimeterOfMercury * 100).roundToInt()
        }
    }

}