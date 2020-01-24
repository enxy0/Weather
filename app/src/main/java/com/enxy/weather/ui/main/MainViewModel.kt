package com.enxy.weather.ui.main

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.enxy.weather.R
import com.enxy.weather.exception.Failure
import com.enxy.weather.ui.main.model.CurrentWeatherModel
import com.enxy.weather.ui.main.model.HourWeatherModel
import com.enxy.weather.ui.search.LocationInfo
import com.enxy.weather.ui.search.LocationRepository
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainViewModel @Inject constructor(
    private val weatherRepository: WeatherRepository,
    private val locationRepository: LocationRepository
) : ViewModel() {
    val currentWeatherModel = MutableLiveData<CurrentWeatherModel>()
    val currentWeatherFailure = MutableLiveData<Failure>()
    val hourWeatherModelArrayList = MutableLiveData<ArrayList<HourWeatherModel>>()
    val hourWeatherFailure = MutableLiveData<Failure>()
    val locationInfoArrayList = MutableLiveData<ArrayList<LocationInfo>>()
    val locationFailure = MutableLiveData<Failure>()
    var currentLocation = LocationInfo(30.26, 59.89)

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
            .handle(::handleCurrentWeatherFailure, ::handleCurrentWeatherSuccess)

    private suspend fun loadHourWeatherForecast(longitude: Double, latitude: Double) =
        weatherRepository.getHourWeatherForecast(longitude, latitude)
            .handle(::handleHourWeatherFailure, ::handleHourWeatherSuccess)

    private fun handleHourWeatherFailure(failure: Failure?) {
        failure?.let {
            this.hourWeatherModelArrayList.value = null
            this.hourWeatherFailure.value = it
        }
    }

    private fun handleHourWeatherSuccess(hourWeatherModelArrayList: ArrayList<HourWeatherModel>?) {
        hourWeatherModelArrayList?.let {
            this.hourWeatherModelArrayList.value = it
            this.hourWeatherFailure.value = null
        }
    }

    private fun handleCurrentWeatherFailure(failure: Failure?) {
        failure?.let {
            this.currentWeatherModel.value = null
            this.currentWeatherFailure.value = it
        }
    }

    private fun handleCurrentWeatherSuccess(currentWeatherModel: CurrentWeatherModel?) {
        currentWeatherModel?.let {
            this.currentWeatherModel.value = it
            this.currentWeatherFailure.value = null
        }
    }

    fun loadTestData() {
        getTestDataCurrentWeatherModel()
        getTestDataHourWeatherModel()
    }

    private fun getTestDataCurrentWeatherModel() {
        currentWeatherModel.value = CurrentWeatherModel(
            "−1",
            "Overcast clouds",
            "-4",
            "3",
            "1012",
            "91",
            R.drawable.current_weather_rain_middle,
            "Saint Petersburg, RU"
        )
    }

    private fun getTestDataHourWeatherModel() {
        val arrayList = arrayListOf(
            HourWeatherModel("-2", "21:00", R.drawable.weather_night_cloudy_rain_light),
            HourWeatherModel("-3", "00:00", R.drawable.weather_scattered_clouds),
            HourWeatherModel("-3", "03:00", R.drawable.weather_night_cloudy),
            HourWeatherModel("+1", "06:00", R.drawable.weather_clear_night),
            HourWeatherModel("+2", "09:00", R.drawable.weather_rain_heavy),
            HourWeatherModel("+3", "12:00", R.drawable.weather_snow_middle),
            HourWeatherModel("+2", "15:00", R.drawable.weather_mist),
            HourWeatherModel("0", "18:00", R.drawable.weather_broken_clouds)
        )
        hourWeatherModelArrayList.value = arrayList
    }
}
