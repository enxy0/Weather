package com.enxy.weather.data

import android.content.Context
import android.content.Context.MODE_PRIVATE
import com.enxy.weather.utils.*
import android.content.SharedPreferences as SharedPreferences1

/**
 * Storage for app and user preferences.
 */
interface AppSettings {
    var temperatureUnit: TemperatureUnit
    var windUnit: WindUnit
    var pressureUnit: PressureUnit
    var theme: Theme
}

/**
 * [AppSettings] impl backed by [android.content.SharedPreferences].
 */
class AppSettingsImpl(context: Context) : AppSettings {
    companion object {
        const val PREF_NAME = "pref_settings"
        const val PREF_DARK_MODE_ENABLED = "pref_dark_mode"
        const val PREF_TEMPERATURE_UNIT = "pref_temperature"
        const val PREF_WIND_UNIT = "pref_wind"
        const val PREF_PRESSURE_UNIT = "pref_pressure"
    }

    private val prefs: Lazy<SharedPreferences1> = lazy {
        context.applicationContext.getSharedPreferences(PREF_NAME, MODE_PRIVATE)
    }

    /**
     * Unit of measurement of temperature.
     * Default unit - celsius
     */
    override var temperatureUnit: TemperatureUnit = TemperatureUnit.CELSIUS
        get() = TemperatureUnit.valueOf(getString(PREF_TEMPERATURE_UNIT, TemperatureUnit.CELSIUS.name))
        set(value) {
            field = value
            saveString(PREF_TEMPERATURE_UNIT, value.name)
        }

    /**
     * Unit of measurement of wind
     * Default unit - meters per second (m/s)
     */
    override var windUnit: WindUnit = WindUnit.METERS_PER_SECOND
        get() = WindUnit.valueOf(getString(PREF_WIND_UNIT, WindUnit.METERS_PER_SECOND.name))
        set(value) {
            field = value
            saveString(PREF_WIND_UNIT, value.name)
        }

    /**
     * Unit of measurement of pressure
     * Default unit - mmHg
     */
    override var pressureUnit: PressureUnit = PressureUnit.MILLIMETERS_OF_MERCURY
        get() = PressureUnit.valueOf(getString(PREF_PRESSURE_UNIT, PressureUnit.MILLIMETERS_OF_MERCURY.name))
        set(value) {
            field = value
            saveString(PREF_PRESSURE_UNIT, value.name)
        }

    /**
     * Theme of the app
     * Default theme - Light
     */
    override var theme: Theme = Theme.LIGHT
        get() = Theme.valueOf(getString(PREF_DARK_MODE_ENABLED, Theme.LIGHT.name))
        set(value) {
            field = value
            ThemeUtils.setAppTheme(value)
            saveString(PREF_DARK_MODE_ENABLED, value.name)
        }

    private fun getString(key: String, def: String) = prefs.value.getString(key, def) ?: def

    private fun saveString(key: String, value: String) {
        prefs.value.edit().putString(key, value).apply()
    }
}