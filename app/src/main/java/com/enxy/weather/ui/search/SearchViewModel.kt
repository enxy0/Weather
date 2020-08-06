package com.enxy.weather.ui.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.enxy.weather.data.entity.Location
import com.enxy.weather.data.repository.LocationRepository
import kotlinx.coroutines.launch

class SearchViewModel(private val locationRepository: LocationRepository) : ViewModel() {

    private val _searchedLocations = MutableLiveData<ArrayList<Location>>()
    val searchedLocations: LiveData<ArrayList<Location>>
        get() = _searchedLocations

    private val _failure = MutableLiveData<Exception>()
    val failure: LiveData<Exception>
        get() = _failure

    /**
     * Loads list of locations by given [locationName]
     */
    fun getLocationsByName(locationName: String) {
        viewModelScope.launch {
            locationRepository.getLocationsByName(locationName).fold(
                onSuccess = { _searchedLocations.postValue(it) },
                onFailure = { _failure.postValue(it) }
            )
        }
    }
}