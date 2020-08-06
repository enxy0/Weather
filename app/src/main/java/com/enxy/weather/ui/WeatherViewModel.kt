package com.enxy.weather.ui

import androidx.lifecycle.*
import com.enxy.weather.data.AppSettings
import com.enxy.weather.data.entity.Forecast
import com.enxy.weather.data.entity.Location
import com.enxy.weather.data.entity.MiniForecast
import com.enxy.weather.data.repository.WeatherRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class WeatherViewModel(
    private val weatherRepository: WeatherRepository,
    val appSettings: AppSettings
) : ViewModel() {

    private val _forecast: MutableLiveData<Forecast> = MutableLiveData()
    val forecast: LiveData<Forecast>
        get() = _forecast

    private val _forecastFailure: MutableLiveData<Exception> = MutableLiveData()
    val forecastFailure: LiveData<Exception>
        get() = _forecastFailure

    private val _isLoading = MutableLiveData<Boolean>(false)
    val isLoading: LiveData<Boolean>
        get() = _isLoading

    val isAppFirstLaunched: LiveData<Boolean> = liveData {
        emit(weatherRepository.isDatabaseEmpty())
    }

    init {
        viewModelScope.launch {
            notifyLoadingStart()
            weatherRepository.getCurrentForecast().fold(
                onSuccess = ::handleFetchSuccess,
                onFailure = ::handleFetchFailure
            )
        }
    }

    fun fetchForecast(location: Location) {
        viewModelScope.launch {
            notifyLoadingStart()
            weatherRepository.getForecastByLocation(location).fold(
                onSuccess = ::handleFetchSuccess,
                onFailure = ::handleFetchFailure
            )
        }
    }

    fun fetchForecast(miniForecast: MiniForecast) {
        viewModelScope.launch {
            notifyLoadingStart()
            weatherRepository.getForecastById(miniForecast.id).fold(
                onSuccess = ::handleFetchSuccess,
                onFailure = ::handleFetchFailure
            )
        }
    }

    fun updateForecast() {
        viewModelScope.launch {
            forecast.value?.let {
                notifyLoadingStart()
                weatherRepository.getUpdatedForecast(it).fold(
                    onSuccess = ::handleFetchSuccess,
                    onFailure = ::handleFetchFailure
                )
            }
        }
    }

    fun changeForecastFavouriteStatus(isFavourite: Boolean) {
        viewModelScope.launch {
            forecast.value?.let {
                it.isFavourite = isFavourite
                weatherRepository.changeForecastFavouriteStatus(it, isFavourite)
            }
        }
    }

    fun applyNewUnits(forecast: Forecast? = _forecast.value) {
        viewModelScope.launch(Dispatchers.Default) {
            _forecast.postValue(forecast?.apply {
                updateTemperatureUnit(appSettings.temperatureUnit)
                updateWindUnit(appSettings.windUnit)
                updatePressureUnit(appSettings.pressureUnit)
            })
        }
    }

    private fun handleFetchSuccess(forecast: Forecast) {
        applyNewUnits(forecast)
        _forecastFailure.value = null // removing old failure if it's not null
        notifyLoadingEnd()
    }

    private fun handleFetchFailure(failure: Exception) {
        _forecastFailure.value = failure
        notifyLoadingEnd()
    }

    private fun notifyLoadingStart() {
        _isLoading.postValue(true)
    }

    private fun notifyLoadingEnd() {
        _isLoading.postValue(false)
    }
}
