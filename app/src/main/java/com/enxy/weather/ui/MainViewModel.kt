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
    val favouriteLocationsList = MutableLiveData<ArrayList<LocationInfo>>()
    val favouriteLocationsFailure = MutableLiveData<Failure>()

    init {
        fetchLastOpenedForecast()
        fetchFavouriteLocations()
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
                    handleForecastSuccess(this)
                }
                is Result.Error -> handleForecastFailure(result.error)
            }
        }
    }

    private fun fetchFavouriteLocations() {
        viewModelScope.launch {
            weatherRepository.getFavouriteLocationsList()
                .handle(::handleFavouriteLocationsFailure, ::handleFavouriteLocationsSuccess)
        }
    }

    fun fetchWeatherForecast(locationInfo: LocationInfo) {
        viewModelScope.launch {
            weatherRepository.getForecast(locationInfo)
                .handle(::handleForecastFailure, ::handleForecastSuccess)
        }
    }

    fun updateWeatherForecast() {
        viewModelScope.launch {
            forecast.value?.let {
                weatherRepository.updateForecast(it)
                    .handle(::handleForecastFailure, ::handleForecastSuccess)
            }
        }
    }

    fun changeForecastFavouriteStatus(isFavourite: Boolean) {
        viewModelScope.launch {
            forecast.value?.let {
                it.isFavourite = isFavourite
                weatherRepository.changeForecastFavouriteStatus(it)
            }
            fetchFavouriteLocations()
        }
    }

    fun fetchListOfLocationsByName(locationName: String) {
        viewModelScope.launch {
            locationRepository.getLocationsByName(locationName)
                .handle(::handleLocationFailure, ::handleLocationSuccess)
        }
    }

    suspend fun isAppFirstLaunched(): Boolean = !weatherRepository.hasCachedForecasts()

    private fun handleForecastSuccess(forecast: Forecast) {
        this.forecast.value = forecast
        this.forecastFailure.value = null
    }

    private fun handleForecastFailure(failure: Failure) {
        this.forecastFailure.value = failure
    }

    private fun handleLocationSuccess(locationList: ArrayList<LocationInfo>) {
        locationInfoArrayList.value = locationList
        locationFailure.value = null
    }

    private fun handleLocationFailure(failure: Failure) {
        locationFailure.value = failure
    }

    private fun handleFavouriteLocationsSuccess(locationList: ArrayList<LocationInfo>) {
        favouriteLocationsList.value = locationList
        favouriteLocationsFailure.value = null
    }

    private fun handleFavouriteLocationsFailure(failure: Failure) {
        favouriteLocationsFailure.value = failure
    }

}
