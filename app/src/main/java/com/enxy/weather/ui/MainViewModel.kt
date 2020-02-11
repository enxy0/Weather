package com.enxy.weather.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.enxy.weather.data.model.Forecast
import com.enxy.weather.data.model.LocationInfo
import com.enxy.weather.data.repository.LocationRepository
import com.enxy.weather.data.repository.WeatherRepository
import com.enxy.weather.exception.Failure
import com.enxy.weather.functional.Result
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

    init {
        fetchLastOpenedForecast()
    }

    private fun fetchLastOpenedForecast() {
        viewModelScope.launch {
            when (val result = weatherRepository.getLastOpenedForecast()) {
                is Result.Success -> with(result.success) {
                    val locationInfo = LocationInfo(
                        locationName,
                        longitude,
                        latitude
                    )
                    fetchWeatherForecast(locationInfo)
                    handleForecastResultSuccess(this)
                }
                is Result.Error -> handleForecastResultFailure(result.error)
            }
        }
    }

    fun fetchWeatherForecast(locationInfo: LocationInfo) {
        viewModelScope.launch {
            weatherRepository.getForecast(locationInfo)
                .handle(::handleForecastResultFailure, ::handleForecastResultSuccess)
        }
    }

    fun updateWeatherForecast() {
        viewModelScope.launch {
            forecast.value?.let {
                weatherRepository.updateForecast(it)
                    .handle(::handleForecastResultFailure, ::handleForecastResultSuccess)
            }
        }
    }

    fun fetchListOfLocationsByName(locationName: String) {
        viewModelScope.launch {
            locationRepository.getLocationsByName(locationName)
                .handle(::handleLocationResultFailure, ::handleLocationResultSuccess)
        }
    }

    suspend fun isAppFirstLaunched(): Boolean = !weatherRepository.hasCachedForecasts()

    private fun handleForecastResultSuccess(forecast: Forecast) {
        this.forecast.value = forecast
        this.forecastFailure.value = null
    }

    private fun handleForecastResultFailure(failure: Failure) {
        this.forecastFailure.value = failure
    }

    private fun handleLocationResultSuccess(locationList: ArrayList<LocationInfo>) {
        locationInfoArrayList.value = locationList
        locationFailure.value = null
    }

    private fun handleLocationResultFailure(failure: Failure) {
        locationFailure.value = failure
    }
}
