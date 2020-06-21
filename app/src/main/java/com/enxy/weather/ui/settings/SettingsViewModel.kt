package com.enxy.weather.ui.settings

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.enxy.weather.data.AppSettings
import com.enxy.weather.utils.Pressure
import com.enxy.weather.utils.Temperature
import com.enxy.weather.utils.Wind

class SettingsViewModel(private val appSettings: AppSettings) : ViewModel() {
    val selectedTemperature: LiveData<Temperature> = liveData {
        emit(appSettings.temperatureUnit)
    }

    val availableTemperatureUnits: LiveData<Array<Temperature>> = liveData {
        emit(Temperature.values())
    }

    val selectedWind: LiveData<Wind> = liveData {
        emit(appSettings.windUnit)
    }

    val availableWindUnits: LiveData<Array<Wind>> = liveData {
        emit(Wind.values())
    }

    val selectedPressure: LiveData<Pressure> = liveData {
        emit(appSettings.pressureUnit)
    }

    val availablePressureUnits: LiveData<Array<Pressure>> = liveData {
        emit(Pressure.values())
    }

    fun updateTemperatureUnit(temperature: Temperature) {
        appSettings.temperatureUnit = temperature
    }

    fun updatePressureUnit(pressure: Pressure) {
        appSettings.pressureUnit = pressure
    }

    fun updateWindUnit(wind: Wind) {
        appSettings.windUnit = wind
    }
}