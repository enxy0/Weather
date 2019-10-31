package com.enxy.weather.ui.main

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.enxy.weather.exception.Failure
import com.enxy.weather.model.CurrentWeatherModel
import com.enxy.weather.model.HourWeatherModel
import com.enxy.weather.repository.WeatherRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancelChildren
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext


class MainViewModel(application: Application) : AndroidViewModel(application), CoroutineScope {
    override val coroutineContext: CoroutineContext = Dispatchers.Main
    private val weatherRepository = WeatherRepository()
    val currentWeatherModel = MutableLiveData<CurrentWeatherModel>()
    val currentWeatherFailure = MutableLiveData<Failure>()
    val hourWeatherModel = MutableLiveData<ArrayList<HourWeatherModel>>()
    val hourWeatherFailure = MutableLiveData<Failure>()

    init {
        getCurrentWeatherModel()
        getHourWeatherModel()
    }

    fun getCurrentWeatherModel() = launch {
        weatherRepository.getCurrentWeatherForecast("498817")
            .handle(::handleCurrentWeatherFailure, ::handleCurrentWeather)
    }

    fun getHourWeatherModel() = launch {
        weatherRepository.getHourWeatherForecast("498817")
            .handle(::handleHourWeatherFailure, ::handleHourWeather)
    }

    fun handleHourWeatherFailure(failure: Failure?) {
        failure?.let {
            this.hourWeatherModel.value = null
            this.hourWeatherFailure.value = it
        }
    }

    fun handleHourWeather(hourWeatherModelArrayList: ArrayList<HourWeatherModel>?) {
        hourWeatherModelArrayList?.let {
            this.hourWeatherModel.value = it
            this.hourWeatherFailure.value = null
        }
    }


    fun handleCurrentWeatherFailure(failure: Failure?) {
        failure?.let {
            this.currentWeatherModel.value = null
            this.currentWeatherFailure.value = it
        }
    }

    fun handleCurrentWeather(currentWeatherModel: CurrentWeatherModel?) {
        currentWeatherModel?.let {
            this.currentWeatherModel.value = it
            this.currentWeatherFailure.value = null
        }
    }

    override fun onCleared() {
        super.onCleared()
        coroutineContext.cancelChildren()
    }
}
