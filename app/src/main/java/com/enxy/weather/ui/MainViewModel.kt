package com.enxy.weather.ui

import androidx.lifecycle.*
import com.enxy.weather.data.AppSettings
import com.enxy.weather.data.entity.Forecast
import com.enxy.weather.data.entity.Location
import com.enxy.weather.repository.LocationRepository
import com.enxy.weather.repository.WeatherRepository
import com.enxy.weather.utils.Result
import com.enxy.weather.utils.exception.Failure
import kotlinx.coroutines.launch

class MainViewModel(
    private val weatherRepository: WeatherRepository,
    private val locationRepository: LocationRepository,
    private val appSettings: AppSettings
) : ViewModel() {
    val forecast = MutableLiveData<Forecast>()
    val forecastFailure = MutableLiveData<Failure>()
    val searchedLocations = MutableLiveData<ArrayList<Location>>()
    val searchedLocationsFailure = MutableLiveData<Failure>()
    val favouriteLocationsList = MutableLiveData<ArrayList<Location>>()
    val favouriteLocationsFailure = MutableLiveData<Failure>()
    val isLoading = MutableLiveData<Boolean>(false)
    val isAppFirstLaunched: LiveData<Boolean> = liveData {
        val result = !weatherRepository.hasCachedForecasts()
        emit(result)
    }
    val settings = liveData { emit(appSettings) }

    init {
        fetchLastOpenedForecast()
        fetchFavouriteLocations()
    }

    private fun fetchLastOpenedForecast() = viewModelScope.launch {
        when (val result = weatherRepository.getLastOpenedForecast()) {
            is Result.Success -> with(result.success) {
                val locationInfo = Location(locationName, longitude, latitude)
                fetchWeatherForecast(locationInfo)
                handleForecastSuccess(this)
            }
            is Result.Error -> handleForecastFailure(result.error)
        }
    }

    private fun fetchFavouriteLocations() = viewModelScope.launch {
        weatherRepository.getFavouriteLocationsList()
            .handle(::handleFavouriteLocationsFailure, ::handleFavouriteLocationsSuccess)
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
        fetchFavouriteLocations()
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

    private fun handleForecastSuccess(forecast: Forecast) {
        this.forecast.value = forecast.inUnits(
            appSettings.temperatureUnit,
            appSettings.windUnit,
            appSettings.pressureUnit
        )
        this.forecastFailure.value = null
        this.isLoading.value = false
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

    private fun handleFavouriteLocationsSuccess(locationList: ArrayList<Location>) {
        favouriteLocationsList.value = locationList
        favouriteLocationsFailure.value = null
    }

    private fun handleFavouriteLocationsFailure(failure: Failure) {
        favouriteLocationsFailure.value = failure
    }
}
