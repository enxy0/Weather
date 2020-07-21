package com.enxy.weather.ui

import  androidx.lifecycle.*
import com.enxy.weather.data.AppSettings
import com.enxy.weather.data.entity.Forecast
import com.enxy.weather.data.entity.Location
import com.enxy.weather.data.repository.LocationRepository
import com.enxy.weather.data.repository.WeatherRepository
import com.enxy.weather.utils.Result
import com.enxy.weather.utils.exception.Failure
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class WeatherViewModel(
    private val weatherRepository: WeatherRepository,
    private val locationRepository: LocationRepository,
    private val appSettings: AppSettings
) : ViewModel() {
    val forecast = MutableLiveData<Forecast>()
    val forecastFailure = MutableLiveData<Failure>()
    val searchedLocations = MutableLiveData<ArrayList<Location>>()
    val searchedLocationsFailure = MutableLiveData<Failure>()
    val isLoading = MutableLiveData<Boolean>(false)
    val isAppFirstLaunched: LiveData<Boolean> = liveData {
        val result = !weatherRepository.hasCachedForecasts()
        emit(result)
    }
    val settings = liveData {
        emit(appSettings)
    }

    init {
        fetchLastOpenedForecast()
    }

    private fun fetchLastOpenedForecast() = viewModelScope.launch {
        weatherRepository.getLastOpenedForecast().onSuccess {
            val locationInfo = Location(data.locationName, data.longitude, data.latitude)
            fetchWeatherForecast(locationInfo)
            handleForecastSuccess(data)
        }.onFailure {
            handleForecastFailure(error)
        }
    }

    fun fetchWeatherForecast(location: Location) = viewModelScope.launch {
        isLoading.value = true
        weatherRepository.getForecast(location)
            .handle(::handleForecastFailure, ::handleForecastSuccess)
    }

    fun updateWeatherForecast() = viewModelScope.launch {
        forecast.value?.let {
            weatherRepository.updateForecast(it)
                .handle(::handleForecastFailure, ::handleForecastSuccess)
        }
    }

    fun changeForecastFavouriteStatus(isFavourite: Boolean) = viewModelScope.launch {
        forecast.value?.let {
            it.isFavourite = isFavourite
            weatherRepository.changeForecastFavouriteStatus(it)
        }
    }

    fun fetchListOfLocationsByName(locationName: String) = viewModelScope.launch {
        locationRepository.getLocationsByName(locationName)
            .handle(::handleLocationFailure, ::handleLocationSuccess)
    }

    /**
     * Fetches opened forecast but with new measure units
     * [handleForecastSuccess] invokes [Forecast.inUnits] to update units
     */
    fun updateForecastUnits() {
        forecast.value?.let {
            fetchWeatherForecast(Location(it.locationName, it.longitude, it.latitude))
        }
    }

    private fun handleForecastSuccess(forecast: Forecast) = viewModelScope.launch {
        this@WeatherViewModel.forecast.value = withContext(Dispatchers.Default) {
            forecast.inUnits(
                appSettings.temperatureUnit,
                appSettings.windUnit,
                appSettings.pressureUnit
            )
        }
        this@WeatherViewModel.forecastFailure.value = null
        this@WeatherViewModel.isLoading.value = false
    }

    private fun handleForecastFailure(failure: Failure) {
        this.forecastFailure.value = failure
        this.isLoading.value = false
    }

    private fun handleLocationSuccess(locationList: ArrayList<Location>) {
        searchedLocations.value = locationList
        searchedLocationsFailure.value = null
    }

    private fun handleLocationFailure(failure: Failure) {
        searchedLocationsFailure.value = failure
    }
}
