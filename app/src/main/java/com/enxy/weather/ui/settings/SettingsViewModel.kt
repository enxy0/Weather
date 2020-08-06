package com.enxy.weather.ui.settings

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.enxy.weather.data.AppSettings
import com.enxy.weather.utils.PressureUnit
import com.enxy.weather.utils.TemperatureUnit
import com.enxy.weather.utils.WindUnit

class SettingsViewModel(private val appSettings: AppSettings) : ViewModel() {
    val selectedTemperature: LiveData<TemperatureUnit> = liveData {
        emit(appSettings.temperatureUnit)
    }

    val availableTemperatureUnits: LiveData<Array<TemperatureUnit>> = liveData {
        emit(TemperatureUnit.values())
    }

    val selectedWind: LiveData<WindUnit> = liveData {
        emit(appSettings.windUnit)
    }

    val availableWindUnits: LiveData<Array<WindUnit>> = liveData {
        emit(WindUnit.values())
    }

    val selectedPressure: LiveData<PressureUnit> = liveData {
        emit(appSettings.pressureUnit)
    }

    val availablePressureUnits: LiveData<Array<PressureUnit>> = liveData {
        emit(PressureUnit.values())
    }

    fun updateTemperatureUnit(temperature: TemperatureUnit) {
        appSettings.temperatureUnit = temperature
    }

    fun updatePressureUnit(pressure: PressureUnit) {
        appSettings.pressureUnit = pressure
    }

    fun updateWindUnit(wind: WindUnit) {
        appSettings.windUnit = wind
    }
}