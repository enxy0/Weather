package com.enxy.weather.ui.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.enxy.weather.data.Forecast
import com.enxy.weather.exception.Failure
import com.enxy.weather.functional.Result
import com.enxy.weather.ui.search.LocationInfo
import com.enxy.weather.ui.search.LocationRepository
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainViewModel @Inject constructor(
    private val weatherRepository: WeatherRepository,
    private val locationRepository: LocationRepository
) : ViewModel() {
    val forecast = MutableLiveData<Forecast>()
    val forecastFailure = MutableLiveData<Failure>()
    val locationInfoArrayList = MutableLiveData<ArrayList<LocationInfo>>()
    val locationFailure = MutableLiveData<Failure>()
    private var currentLocation = WeatherRepository.DEFAULT_LOCATION

    init {
        fetchWeatherForecast(currentLocation)
    }

    fun updateWeatherLocation(locationInfo: LocationInfo) {
        currentLocation = locationInfo
        fetchWeatherForecast(locationInfo)
    }

    fun fetchWeatherForecast(locationInfo: LocationInfo = currentLocation) {
        viewModelScope.launch {
            // fetch forecast from repository
            val result: Result<Failure, Forecast> =
                weatherRepository.getForecast(locationInfo.longitude, locationInfo.latitude)
            // It either Result.Success or Result.Failure
            when (result) {
                is Result.Success -> {
                    forecast.value = result.success
                    forecastFailure.value = null
                }
                is Result.Error -> forecastFailure.value = result.error
            }
        }
    }

    fun fetchListOfLocationsByName(locationName: String) {
        viewModelScope.launch {
            // fetch locations list form repository
            val result: Result<Failure, ArrayList<LocationInfo>> =
                locationRepository.getLocationsByName(locationName)
            // It either Result.Success or Result.Failure
            when (result) {
                is Result.Success -> {
                    locationInfoArrayList.value = result.success
                    locationFailure.value = null
                }
                is Result.Error -> locationFailure.value = result.error
            }
        }
    }
}
