package com.enxy.weather.ui.main

import com.enxy.weather.BuildConfig
import com.enxy.weather.base.BaseRepository
import com.enxy.weather.exception.Failure
import com.enxy.weather.functional.Result
import com.enxy.weather.network.ImageChooser
import com.enxy.weather.network.NetworkService
import com.enxy.weather.network.json.current.CurrentWeatherResponse
import com.enxy.weather.network.json.hour.HourWeatherResponse
import com.enxy.weather.ui.main.model.CurrentWeatherModel
import com.enxy.weather.ui.main.model.HourWeatherModel
import javax.inject.Inject
import kotlin.math.roundToInt

class WeatherRepository @Inject constructor(private val service: NetworkService) :
    BaseRepository() {
    companion object {
        // OpenWeatherMap API URL queries
        const val APPID = BuildConfig.API_KEY
        const val LANGUAGE = "ENG"
        const val UNITS = "metric"
        const val CURRENT_WEATHER_TYPE = "weather"
        const val CURRENT_WEATHER_COUNT = 0
        const val THREE_HOUR_WEATHER_TYPE = "forecast"
        const val THREE_HOUR_WEATHER_COUNT = 8
    }

    suspend fun getCurrentWeatherForecast(id: String): Result<Failure, CurrentWeatherModel> {

        return safeApiCall(
            call = {
                service.weatherApi().getCurrentWeatherAsync(
                    CURRENT_WEATHER_TYPE,
                    APPID,
                    id,
                    CURRENT_WEATHER_COUNT,
                    LANGUAGE,
                    UNITS
                )
            },
            transform = ::transformCurrentWeatherResponse
        )
    }

    suspend fun getHourWeatherForecast(
        id: String
    ): Result<Failure, ArrayList<HourWeatherModel>> {
        return safeApiCall(
            call = {
                service.weatherApi().getHourWeatherAsync(
                    THREE_HOUR_WEATHER_TYPE,
                    APPID,
                    id,
                    THREE_HOUR_WEATHER_COUNT,
                    LANGUAGE,
                    UNITS
                )
            },
            transform = ::transformHourWeatherResponse
        )
    }

    private fun transformHourWeatherResponse(hourWeatherResponse: HourWeatherResponse): ArrayList<HourWeatherModel> {
        val hourWeatherModelArrayList = ArrayList<HourWeatherModel>()
        for (hourListItem in hourWeatherResponse.list) {
            val temperature: String
            hourListItem.main.temp.roundToInt().let {
                temperature = when {
                    it > 0 -> "+$it"
                    else -> it.toString()
                }
            }
            val time = hourListItem.dtTxt.substring(11, 16)
            val imageCode = hourListItem.weather[0].id
            val dayPart: Char
            hourListItem.weather[0].icon.let {
                dayPart = it[it.length - 1]
            }
            val imageId = ImageChooser.OpenWeatherMap.getImageIdHourWeather(imageCode, dayPart)
            hourWeatherModelArrayList.add(HourWeatherModel(temperature, time, imageId))
        }
        return hourWeatherModelArrayList
    }

    private fun transformCurrentWeatherResponse(currentWeatherResponse: CurrentWeatherResponse): CurrentWeatherModel {
        val temperature: String
        currentWeatherResponse.main.temp.roundToInt().let {
            temperature = when {
                it > 0 -> "+$it"
                else -> it.toString()
            }
        }
        val feelsLikeTemperature =
            "${(currentWeatherResponse.main.temp - currentWeatherResponse.wind.speed).roundToInt()}"
        val wind = currentWeatherResponse.wind.speed.roundToInt().toString()
        val description = currentWeatherResponse.weather[0].description.capitalize()
        val pressure = currentWeatherResponse.main.pressure.toString()
        val humidity = currentWeatherResponse.main.humidity.toString()
        val imageCode = currentWeatherResponse.weather[0].id
        val dayPart: Char
        currentWeatherResponse.weather[0].icon.let {
            dayPart = it[it.length - 1]
        }
        val imageId = ImageChooser.OpenWeatherMap.getImageIdCurrentWeather(imageCode, dayPart)
        val cityName = "${currentWeatherResponse.name}, ${currentWeatherResponse.sys.country}"
        return CurrentWeatherModel(
            temperature,
            description,
            feelsLikeTemperature,
            wind,
            pressure,
            humidity,
            imageId,
            cityName
        )
    }
}