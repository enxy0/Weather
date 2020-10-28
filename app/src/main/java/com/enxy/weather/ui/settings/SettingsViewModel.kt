package com.enxy.weather.ui.settings

import androidx.lifecycle.ViewModel
import com.enxy.weather.data.AppSettings
import com.enxy.weather.utils.Pressure
import com.enxy.weather.utils.Temperature
import com.enxy.weather.utils.Theme
import com.enxy.weather.utils.Wind

class SettingsViewModel(private val appSettings: AppSettings) : ViewModel() {
    val temperature: String = appSettings.temperature.displayedName
    val availableTemperatureUnits: Array<Temperature> = Temperature.values()

    val wind: String = appSettings.wind.displayedName
    val availableWindUnits: Array<Wind> = Wind.values()

    val pressure: String = appSettings.pressure.displayedName
    val availablePressureUnits: Array<Pressure> = Pressure.values()

    val theme: String = appSettings.theme.displayedName
    val availableThemes: Array<Theme> = Theme.values()

    fun updateTemperatureUnit(temperature: Temperature) {
        appSettings.temperature = temperature
    }

    fun updatePressureUnit(pressure: Pressure) {
        appSettings.pressure = pressure
    }

    fun updateWindUnit(wind: Wind) {
        appSettings.wind = wind
    }

    fun updateTheme(theme: Theme) {
        appSettings.theme = theme
    }
}