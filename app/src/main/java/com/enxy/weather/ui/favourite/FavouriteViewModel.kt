package com.enxy.weather.ui.favourite

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.enxy.weather.data.entity.Location
import com.enxy.weather.data.repository.WeatherRepository
import com.enxy.weather.utils.exception.Failure
import kotlinx.coroutines.launch

class FavouriteViewModel(private val weatherRepository: WeatherRepository) : ViewModel() {

    private val _favouriteLocations = MutableLiveData<ArrayList<Location>>()
    val favouriteLocations: LiveData<ArrayList<Location>>
        get() = _favouriteLocations

    private val _favouriteLocationsFailure: MutableLiveData<Failure> = MutableLiveData()
    val favouriteLocationsFailure: LiveData<Failure>
        get() = _favouriteLocationsFailure

    init {
        viewModelScope.launch {
            weatherRepository.getFavouriteLocationsList().onSuccess {
                _favouriteLocations.value = data
            }.onFailure {
                _favouriteLocationsFailure.value = error
            }
        }
    }
}