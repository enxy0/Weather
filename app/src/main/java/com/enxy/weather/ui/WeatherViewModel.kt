package com.enxy.weather.ui

import androidx.lifecycle.*
import com.enxy.weather.data.AppSettings
import com.enxy.weather.data.entity.Forecast
import com.enxy.weather.data.entity.Location
import com.enxy.weather.data.repository.LocationRepository
import com.enxy.weather.data.repository.WeatherRepository
import com.enxy.weather.utils.exception.Failure
import com.enxy.weather.utils.extension.toLocation
import kotlinx.coroutines.launch

class WeatherViewModel(
    private val weatherRepository: WeatherRepository,
    private val locationRepository: LocationRepository,
    private val appSettings: AppSettings
) : ViewModel() {

    private val _forecast = MutableLiveData<Forecast>()
    val forecast: LiveData<Forecast>
        get() = _forecast

    private val _forecastFailure = MutableLiveData<Failure>()
    val forecastFailure: LiveData<Failure>
        get() = _forecastFailure

    private val _searchedLocations = MutableLiveData<ArrayList<Location>>()
    val searchedLocations: LiveData<ArrayList<Location>>
        get() = _searchedLocations

    private val _searchedLocationsFailure = MutableLiveData<Failure>()
    val searchedLocationsFailure: LiveData<Failure>
        get() = _searchedLocationsFailure

    private val _isLoading = MutableLiveData<Boolean>(false)
    val isLoading: LiveData<Boolean>
        get() = _isLoading

    val isAppFirstLaunched: LiveData<Boolean> = liveData {
        emit(weatherRepository.isDatabaseEmpty())
    }
    val settings = liveData {
        emit(appSettings)
    }

    init {
        fetchLastOpenedForecast()
    }

    private fun fetchLastOpenedForecast() {
        viewModelScope.launch {
            weatherRepository.getLastOpenedForecast().onSuccess {
                fetchWeatherForecast(it.toLocation())
                onForecastSuccess(it)
            }.onFailure {
                onForecastFailure(it)
            }
        }
    }

    fun fetchWeatherForecast(location: Location) {
        viewModelScope.launch {
            _isLoading.value = true
            weatherRepository
                .getForecast(location)
                .handle(::onForecastFailure, ::onForecastSuccess)
        }
    }

    fun updateWeatherForecast() = viewModelScope.launch {
        _forecast.value?.let {
            weatherRepository
                .updateForecast(it)
                .handle(::onForecastFailure, ::onForecastSuccess)
        }
    }

    fun changeForecastFavouriteStatus(isFavourite: Boolean) = viewModelScope.launch {
        _forecast.value?.let {
            it.isFavourite = isFavourite
            weatherRepository.changeForecastFavouriteStatus(it, isFavourite)
        }
    }

    fun fetchListOfLocationsByName(locationName: String) {
        viewModelScope.launch {
            locationRepository
                .getLocationsByName(locationName)
                .onSuccess {
                    _searchedLocations.value = it
                    _searchedLocationsFailure.value = null
                }.onFailure {
                    _searchedLocationsFailure.value = it
                }
        }
    }

    /**
     * Fetches opened forecast but with new measure units
     * [onForecastSuccess] invokes [Forecast.inUnits] to update units
     */
    fun updateForecastUnits() {
        _forecast.value?.let {
            fetchWeatherForecast(it.toLocation())
        }
    }

    private fun onForecastSuccess(forecast: Forecast) {
        viewModelScope.launch {
            _forecast.value = forecast.inUnits(
                appSettings.temperatureUnit,
                appSettings.windUnit,
                appSettings.pressureUnit
            )
            _forecastFailure.value = null
            _isLoading.value = false
        }
    }

    private fun onForecastFailure(failure: Failure) {
        _forecastFailure.value = failure
        _isLoading.value = false
    }
}
