package com.enxy.weather.ui.main

import com.enxy.weather.BuildConfig
import com.enxy.weather.base.BaseRepository
import com.enxy.weather.data.CurrentForecast
import com.enxy.weather.data.Forecast
import com.enxy.weather.data.Hour
import com.enxy.weather.data.HourForecast
import com.enxy.weather.exception.Failure
import com.enxy.weather.functional.Result
import com.enxy.weather.network.ImageChooser
import com.enxy.weather.network.NetworkService
import com.enxy.weather.network.json.openweathermap.current.CurrentForecastResponse
import com.enxy.weather.network.json.openweathermap.hour.HourForecastResponse
import com.enxy.weather.ui.search.LocationInfo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.math.roundToInt

@Singleton
class WeatherRepository @Inject constructor(private val service: NetworkService) :
    BaseRepository() {
    companion object {
        // OpenWeatherMap API URL queries
        const val OPEN_WEATHER_MAP_APPID = BuildConfig.API_KEY_OPEN_WEATHER_MAP
        const val DEFAULT_LANGUAGE = "ENG"
        const val DEFAULT_UNITS = "metric"
        const val THREE_HOUR_WEATHER_COUNT = 8 // 3 x 8 = 24 hour weather forecast
        val DEFAULT_LOCATION =
            LocationInfo(30.2642, 59.8944) // Temporary default location (Saint-Petersburg, RU)
    }

    suspend fun getForecast(
        longitude: Double,
        latitude: Double
    ): Result<Failure, Forecast> {
        val currentForecast: Result<Failure, CurrentForecast> =
            getCurrentWeatherForecast(longitude, latitude)
        val hourForecast: Result<Failure, HourForecast> =
            getHourWeatherForecast(longitude, latitude)
        return when {
            currentForecast is Result.Error -> Result.Error(currentForecast.error)
            hourForecast is Result.Error -> Result.Error(hourForecast.error)
            else -> transformToForecast(
                (currentForecast as Result.Success).success,
                (hourForecast as Result.Success).success
            )
        }
    }

    private suspend fun transformToForecast(
        currentForecast: CurrentForecast,
        hourForecast: HourForecast
    ): Result<Failure, Forecast> = withContext(Dispatchers.Default) {
        val cityName = currentForecast.cityName
        val longitude = currentForecast.latitude
        val latitude = currentForecast.latitude
        Result.Success(
            Forecast(
                cityName = cityName,
                longitude = longitude,
                latitude = latitude,
                currentForecast = currentForecast,
                hourForecast = hourForecast
            )
        )
    }

    suspend fun getCurrentWeatherForecast(
        longitude: Double,
        latitude: Double
    ): Result<Failure, CurrentForecast> {
        return safeApiCall(
            call = {
                service.weatherApi().getCurrentForecastAsync(
                    longitude = longitude,
                    latitude = latitude,
                    APPID = OPEN_WEATHER_MAP_APPID,
                    language = DEFAULT_LANGUAGE,
                    units = DEFAULT_UNITS
                )
            },
            transform = ::transformCurrentForecastResponse
        )
    }

    suspend fun getHourWeatherForecast(
        longitude: Double,
        latitude: Double
    ): Result<Failure, HourForecast> {
        return safeApiCall(
            call = {
                service.weatherApi().getHourForecastAsync(
                    longitude = longitude,
                    latitude = latitude,
                    APPID = OPEN_WEATHER_MAP_APPID,
                    count = THREE_HOUR_WEATHER_COUNT,
                    language = DEFAULT_LANGUAGE,
                    units = DEFAULT_UNITS
                )
            },
            transform = ::transformHourForecastResponse
        )
    }

    private fun transformHourForecastResponse(hourForecastResponse: HourForecastResponse): HourForecast {
        val hourList = ArrayList<Hour>(hourForecastResponse.list.size)
        val longitude = hourForecastResponse.city.coord.lon
        val latitude = hourForecastResponse.city.coord.lat
        val cityName = "${hourForecastResponse.city.name}, ${hourForecastResponse.city.country}"
        for (hourListItem in hourForecastResponse.list) {
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
            val imageId = ImageChooser.OpenWeatherMap.getImageIdHourForecast(imageCode, dayPart)
            hourList.add(Hour(temperature = temperature, time = time, imageId = imageId))
        }
        return HourForecast(
            cityName = cityName,
            longitude = longitude,
            latitude = latitude,
            hourArrayList = hourList
        )
    }

    private fun transformCurrentForecastResponse(currentForecastResponse: CurrentForecastResponse): CurrentForecast {
        val longitude = currentForecastResponse.coord.lon
        val latitude = currentForecastResponse.coord.lat
        val cityName = "${currentForecastResponse.name}, ${currentForecastResponse.sys.country}"
        val temperature: String
        currentForecastResponse.main.temp.roundToInt().let {
            temperature = when {
                it > 0 -> "+$it"
                else -> it.toString()
            }
        }
        val feelsLikeTemperature = currentForecastResponse.main.feelsLike.roundToInt().toString()
        val wind = currentForecastResponse.wind.speed.roundToInt().toString()
        val description = currentForecastResponse.weather[0].description.capitalize()
        val pressure = currentForecastResponse.main.pressure.toString()
        val humidity = currentForecastResponse.main.humidity.toString()
        val imageCode = currentForecastResponse.weather[0].id
        val dayPart: Char
        currentForecastResponse.weather[0].icon.let { dayPart = it[it.length - 1] }
        val imageId = ImageChooser.OpenWeatherMap.getImageIdCurrentForecast(imageCode, dayPart)
        return CurrentForecast(
            cityName = cityName,
            longitude = longitude,
            latitude = latitude,
            temperature = temperature,
            description = description,
            feelsLikeTemperature = feelsLikeTemperature,
            wind = wind,
            pressure = pressure,
            humidity = humidity,
            imageId = imageId
        )
    }
}