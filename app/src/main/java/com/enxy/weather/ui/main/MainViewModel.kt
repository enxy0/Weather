package com.enxy.weather.ui.main

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
) :
    ViewModel() {
    val currentWeatherModel = MutableLiveData<CurrentWeatherModel>()
    val currentWeatherFailure = MutableLiveData<Failure>()
    val hourWeatherModelArrayList = MutableLiveData<ArrayList<HourWeatherModel>>()
    val hourWeatherFailure = MutableLiveData<Failure>()
    val locationInfoArrayList = MutableLiveData<ArrayList<LocationInfo>>()
    val locationFailure = MutableLiveData<Failure>()

    init {
//        loadTestData()
        updateWeatherForecast()
    }

    fun updateWeatherForecast() {
        viewModelScope.launch {
            loadCurrentWeatherForecast()
            loadHourWeatherForecast()
        }
    }

    fun fetchListOfLocationsByName(locationName: String) {
        viewModelScope.launch {
            locationRepository.getLocationByName(locationName)
                .handle(::handleFindLocationFailure, ::handleFindLocationSuccess)
        }
    }

    private fun handleFindLocationFailure(failure: Failure?) {
        failure?.let {
            this.locationFailure.value = it
        }
    }

    private fun handleFindLocationSuccess(locationInfoArrayList: ArrayList<LocationInfo>?) {
        locationInfoArrayList?.let {
            this.locationInfoArrayList.value = it
        }
    }

    private suspend fun loadCurrentWeatherForecast() =
        weatherRepository.getCurrentWeatherForecast("498817")
            .handle(::handleCurrentWeatherFailure, ::handleCurrentWeatherSuccess)


    private suspend fun loadHourWeatherForecast() =
        weatherRepository.getHourWeatherForecast("498817")
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
