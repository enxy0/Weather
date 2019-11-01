package com.enxy.weather.repository

import com.enxy.weather.BuildConfig
import com.enxy.weather.base.BaseRepository
import com.enxy.weather.exception.Failure
import com.enxy.weather.functional.Result
import com.enxy.weather.model.CurrentWeatherModel
import com.enxy.weather.model.DayWeatherModel
import com.enxy.weather.model.HourWeatherModel
import com.enxy.weather.network.ImageChooser
import com.enxy.weather.network.NetworkService
import com.enxy.weather.network.json.current.CurrentWeatherResponse
import com.enxy.weather.network.json.hour.HourWeatherResponse
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList
import kotlin.math.abs
import kotlin.math.roundToInt

class WeatherRepository : BaseRepository() {
    companion object {
        const val APPID = BuildConfig.API_KEY
        const val LANGUAGE = "ENG"
        const val UNITS = "metric"
        const val CURRENT_WEATHER_TYPE = "weather"
        const val CURRENT_WEATHER_COUNT = 0

        const val THREE_HOUR_WEATHER_TYPE = "forecast"
        const val THREE_HOUR_WEATHER_COUNT = 8

        const val DAY_WEATHER_TYPE = "forecast"
        const val DAY_WEATHER_COUNT = 40
    }

    suspend fun getCurrentWeatherForecast(
        id: String
    ): Result<Failure, CurrentWeatherModel> {
        return safeApiCall(
            call = {
                NetworkService.getApi().getCurrentWeatherAsync(
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
                NetworkService.getApi().getHourWeatherAsync(
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

    suspend fun getDayWeatherForecast(
        id: String
    ): Result<Failure, ArrayList<DayWeatherModel>> {
        return safeApiCall(
            call = {
                NetworkService.getApi().getHourWeatherAsync(
                    DAY_WEATHER_TYPE,
                    APPID,
                    id,
                    DAY_WEATHER_COUNT,
                    LANGUAGE,
                    UNITS
                )
            },
            transform = ::transformDayWeatherResponse
        )
    }

    private fun transformDayWeatherResponse(hourWeatherResponse: HourWeatherResponse): ArrayList<DayWeatherModel> {
        // TODO: finish transform function
        val hourWeatherModelArrayList = ArrayList<DayWeatherModel>()
        val calendar = Calendar.getInstance()
        for (hourListItem in hourWeatherResponse.list) {
            val date = Date(hourListItem.dt)
            val day = SimpleDateFormat("EEEE", Locale.ENGLISH).format(date.time)
            val temperatureMax: String
            hourListItem.main.tempMax.roundToInt().let {
                temperatureMax = when {
                    it > 0 -> "+$it"
                    it < 0 -> "−${abs(it)}"
                    else -> "$it"
                }
            }
            val temperatureMin: String
            hourListItem.main.tempMin.roundToInt().let {
                temperatureMin = when {
                    it > 0 -> "+$it"
                    it < 0 -> "−${abs(it)}"
                    else -> "$it"
                }
            }
            val time = hourListItem.dtTxt.substring(11, 16)
            val imageCode = hourListItem.weather[0].id
            val dayPart: Char
            hourListItem.weather[0].icon.let {
                dayPart = it[it.length - 1]
            }
            val imageId = ImageChooser.OpenWeatherMap.getImageIdHourWeather(imageCode, dayPart)
            hourWeatherModelArrayList.add(
                DayWeatherModel(
                    day,
                    temperatureMax,
                    temperatureMin,
                    time,
                    imageId
                )
            )
        }
        return hourWeatherModelArrayList
    }

    private fun transformHourWeatherResponse(hourWeatherResponse: HourWeatherResponse): ArrayList<HourWeatherModel> {
        val hourWeatherModelArrayList = ArrayList<HourWeatherModel>()
        for (hourListItem in hourWeatherResponse.list) {
            val temperature: String
            hourListItem.main.temp.roundToInt().let {
                temperature = when {
                    it > 0 -> "+$it"
                    it < 0 -> "−${abs(it)}"
                    else -> "$it"
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
                it > 0 -> "+$it°"
                it < 0 -> "−$it°"
                else -> "$it°"
            }
        }
        val feelsLikeTemperature =
            "${(currentWeatherResponse.main.temp - currentWeatherResponse.wind.speed).roundToInt()}°"
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