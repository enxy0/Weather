package com.enxy.weather.ui.favourite

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.enxy.weather.data.entity.MiniForecast
import com.enxy.weather.data.repository.WeatherRepository
import kotlinx.coroutines.launch

class FavouriteViewModel(weatherRepository: WeatherRepository) : ViewModel() {

    private val _favouriteLocations: MutableLiveData<List<MiniForecast>> = MutableLiveData()
    val favouriteLocations: LiveData<List<MiniForecast>>
        get() = _favouriteLocations

    private val _failure: MutableLiveData<Exception> = MutableLiveData()
    val failure: LiveData<Exception>
        get() = _failure


    init {
        viewModelScope.launch {
            weatherRepository.getFavouriteLocations().fold(
                onSuccess = { _favouriteLocations.value = it },
                onFailure = { _failure.value = it }
            )
        }
    }
}
