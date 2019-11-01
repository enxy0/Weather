package com.enxy.weather.ui.main

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.enxy.weather.R
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
    val hourWeatherModelArrayList = MutableLiveData<ArrayList<HourWeatherModel>>()
    val hourWeatherFailure = MutableLiveData<Failure>()

    init {
        getTestDataCurrentWeatherModel()
        getTestDataHourWeatherModel()
//        getCurrentWeatherModel()
//        getHourWeatherModelArrayList()
    }

    private fun getTestDataCurrentWeatherModel() {
        currentWeatherModel.value = CurrentWeatherModel(
            "−1°",
            "Overcast clouds",
            "-4°",
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
            this.hourWeatherModelArrayList.value = null
            this.hourWeatherFailure.value = it
        }
    }

    fun handleHourWeather(hourWeatherModelArrayList: ArrayList<HourWeatherModel>?) {
        hourWeatherModelArrayList?.let {
            this.hourWeatherModelArrayList.value = it
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
