package com.enxy.weather.ui.main

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.enxy.weather.data.CurrentForecast
import com.enxy.weather.data.HourForecast
import com.enxy.weather.exception.Failure
import com.enxy.weather.ui.search.LocationInfo
import com.enxy.weather.ui.search.LocationRepository
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainViewModel @Inject constructor(
    private val weatherRepository: WeatherRepository,
    private val locationRepository: LocationRepository
) : ViewModel() {
    val currentWeather = MutableLiveData<CurrentForecast>()
    val currentWeatherFailure = MutableLiveData<Failure>()
    val hourForecast = MutableLiveData<HourForecast>()
    val hourForecastFailure = MutableLiveData<Failure>()
    val locationInfoArrayList = MutableLiveData<ArrayList<LocationInfo>>()
    val locationFailure = MutableLiveData<Failure>()
    private var currentLocation = LocationInfo(30.2642, 59.8944)

    init {
        fetchWeatherForecast(currentLocation)
    }

    fun updateWeatherLocation(locationInfo: LocationInfo) {
        currentLocation = locationInfo
        fetchWeatherForecast(locationInfo)
    }

    fun fetchWeatherForecast(locationInfo: LocationInfo = currentLocation) {
        viewModelScope.launch {
            loadCurrentWeatherForecast(locationInfo.longitude, locationInfo.latitude)
            loadHourWeatherForecast(locationInfo.longitude, locationInfo.latitude)
        }
    }

    fun fetchListOfLocationsByName(locationName: String) {
        Log.d("MainViewModel", "fetchListOfLocationsByName: called")
        viewModelScope.launch {
            locationRepository.getLocationsByName(locationName)
                .handle(::handleFindLocationFailure, ::handleFindLocationSuccess)
        }
    }

    private fun handleFindLocationFailure(failure: Failure?) {
        failure?.let {
            this.locationFailure.value = it
            this.locationInfoArrayList.value = null
        }
    }

    private fun handleFindLocationSuccess(locationInfoArrayList: ArrayList<LocationInfo>?) {
        locationInfoArrayList?.let {
            this.locationInfoArrayList.value = it
            this.locationFailure.value = null
        }
    }

    private suspend fun loadCurrentWeatherForecast(longitude: Double, latitude: Double) =
        weatherRepository.getCurrentWeatherForecast(longitude, latitude)
            .handle(::handleCurrentForecastFailure, ::handleCurrentForecastSuccess)

    private suspend fun loadHourWeatherForecast(longitude: Double, latitude: Double) =
        weatherRepository.getHourWeatherForecast(longitude, latitude)
            .handle(::handleHourForecastFailure, ::handleHourForecastSuccess)

    private fun handleHourForecastFailure(failure: Failure?) {
        failure?.let {
            this.hourForecast.value = null
            this.hourForecastFailure.value = it
        }
    }

    private fun handleHourForecastSuccess(hourForecast: HourForecast?) {
        hourForecast?.let {
            this.hourForecast.value = it
            this.hourForecastFailure.value = null
        }
    }

    private fun handleCurrentForecastFailure(failure: Failure?) {
        failure?.let {
            this.currentWeather.value = null
            this.currentWeatherFailure.value = it
        }
    }

    private fun handleCurrentForecastSuccess(currentForecast: CurrentForecast?) {
        currentForecast?.let {
            this.currentWeather.value = it
            this.currentWeatherFailure.value = null
        }
    }
}
