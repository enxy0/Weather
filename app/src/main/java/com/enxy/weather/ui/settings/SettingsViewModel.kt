package com.enxy.weather.ui.settings

import androidx.lifecycle.ViewModel
import com.enxy.weather.data.AppSettings
import com.enxy.weather.utils.PressureUnit
import com.enxy.weather.utils.TemperatureUnit
import com.enxy.weather.utils.Theme
import com.enxy.weather.utils.WindUnit

class SettingsViewModel(private val appSettings: AppSettings) : ViewModel() {
    val temperature: String = appSettings.temperatureUnit.displayedName
    val availableTemperatureUnits: List<String> = TemperatureUnit.values().map { it.displayedName }

    val wind: String = appSettings.windUnit.displayedName
    val availableWindUnits: List<String> = WindUnit.values().map { it.displayedName }

    val pressure: String = appSettings.pressureUnit.displayedName
    val availablePressureUnits: List<String> = PressureUnit.values().map { it.displayedName }

    val theme: String = appSettings.theme.displayedName
    val availableThemes: List<String> = Theme.values().map { it.displayedName }

    fun updateTemperatureUnit(index: Int) {
        appSettings.temperatureUnit = TemperatureUnit.values()[index]
    }

    fun updatePressureUnit(index: Int) {
        appSettings.pressureUnit = PressureUnit.values()[index]
    }

    fun updateWindUnit(index: Int) {
        appSettings.windUnit = WindUnit.values()[index]
    }

    fun updateTheme(index: Int) {
        appSettings.theme = Theme.values()[index]
    }
}