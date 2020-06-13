package com.enxy.weather.repository

import com.enxy.weather.BuildConfig
import com.enxy.weather.base.NetworkRepository
import com.enxy.weather.data.AppDataBase
import com.enxy.weather.data.entity.CurrentForecast
import com.enxy.weather.data.entity.Forecast
import com.enxy.weather.data.entity.HourForecast
import com.enxy.weather.data.entity.Location
import com.enxy.weather.network.ImageChooser
import com.enxy.weather.network.NetworkService
import com.enxy.weather.network.json.openweathermap.current.CurrentForecastResponse
import com.enxy.weather.network.json.openweathermap.hour.HourForecastResponse
import com.enxy.weather.utils.Result
import com.enxy.weather.utils.exception.Failure
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlin.math.roundToInt

class WeatherRepository(
    private val service: NetworkService,
    private val database: AppDataBase
) : NetworkRepository {
    companion object {
        // OpenWeatherMap API URL queries
        const val OPEN_WEATHER_MAP_APPID = BuildConfig.API_KEY_OPEN_WEATHER_MAP
        const val DEFAULT_LANGUAGE = "ENG"
        const val DEFAULT_UNITS = "metric"
        const val THREE_HOUR_WEATHER_COUNT = 8 // 3 x 8 = 24 hour weather forecast
    }

    suspend fun getForecast(location: Location): Result<Failure, Forecast> {
        database.getForecastDao().updateLastOpenedForecast(location.locationName)
        val isForecastCached: Boolean = database.getForecastDao()
            .isForecastCached(location.locationName)
        if (isForecastCached) {
            val forecast: Forecast =
                database.getForecastDao().getForecastByLocationName(location.locationName)
            return if (forecast.isNotOutdated()) {
                Result.Success(forecast)
            } else {
                val result: Result<Failure, Forecast> = requestForecast(location)
                if (result is Result.Success) {
                    result.success.isFavourite = forecast.isFavourite
                    database.getForecastDao().updateForecast(result.success)
                }
                result
            }
        } else {
            val result: Result<Failure, Forecast> = requestForecast(location)
            if (result is Result.Success) {
                database.getForecastDao().insertForecast(result.success)
            }
            return result
        }
    }

    suspend fun updateForecast(forecast: Forecast): Result<Failure, Forecast> {
        val location = Location(forecast.locationName, forecast.longitude, forecast.latitude)
        val result: Result<Failure, Forecast> = requestForecast(location)
        if (result is Result.Success) {
            result.success.isFavourite = forecast.isFavourite
            database.getForecastDao().updateForecast(result.success)
        }
        return result
    }

    suspend fun getLastOpenedForecast(): Result<Failure, Forecast> {
        return if (hasCachedForecasts())
            Result.Success(database.getForecastDao().getLastOpenedForecast())
        else
            Result.Error(Failure.DataNotFoundInCache)
    }

    suspend fun hasCachedForecasts(): Boolean = database.getForecastDao().hasCachedForecasts()

    suspend fun getFavouriteLocationsList(): Result<Failure, ArrayList<Location>> {
        val favouriteForecasts = database.getForecastDao().getFavouriteForecastList()
        return if (favouriteForecasts != null)
            withContext(Dispatchers.Default) {
                Result.Success(
                    favouriteForecasts.map {
                        Location(
                            locationName = it.locationName,
                            latitude = it.latitude,
                            longitude = it.longitude
                        )
                    } as ArrayList
                )
            }
        else
            Result.Error(Failure.DataNotFoundInCache)
    }

    suspend fun changeForecastFavouriteStatus(forecast: Forecast) {
        database.getForecastDao()
            .setForecastFavouriteStatus(forecast.longitude, forecast.latitude, forecast.isFavourite)
    }

    private suspend fun requestForecast(location: Location): Result<Failure, Forecast> {
        val currentForecast: Result<Failure, CurrentForecast> =
            requestCurrentWeatherForecast(location.longitude, location.latitude)
        val hourForecastList: Result<Failure, ArrayList<HourForecast>> =
            requestHourWeatherForecast(location.longitude, location.latitude)
        return when {
            currentForecast is Result.Error -> Result.Error(currentForecast.error)
            hourForecastList is Result.Error -> Result.Error(hourForecastList.error)
            else -> withContext(Dispatchers.Default) {
                Result.Success(
                    Forecast(
                        locationName = location.locationName,
                        longitude = location.longitude,
                        latitude = location.latitude,
                        currentForecast = (currentForecast as Result.Success).success,
                        hourForecastList = (hourForecastList as Result.Success).success
                    )
                )
            }
        }
    }

    private suspend fun requestCurrentWeatherForecast(
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
            transform = ::responseToCurrentForecast
        )
    }

    private suspend fun requestHourWeatherForecast(
        longitude: Double,
        latitude: Double
    ): Result<Failure, ArrayList<HourForecast>> {
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
            transform = ::responseToHourForecastList
        )
    }

    private fun responseToCurrentForecast(response: CurrentForecastResponse): CurrentForecast {
        val temperature = response.main.temp.roundToInt().let { if (it > 0) "+$it" else "$it" }
        val feelsLikeTemperature = response.main.feelsLike.roundToInt().toString()
        val wind = response.wind.speed.roundToInt().toString()
        val description = response.weather[0].description.capitalize()
        val pressure = response.main.pressure.toString()
        val humidity = response.main.humidity.toString()
        val imageCode = response.weather[0].id
        val dayPart = response.weather[0].icon.let { it[it.length - 1] }
        val imageId = ImageChooser.OpenWeatherMap.getImageIdCurrentForecast(imageCode, dayPart)
        return CurrentForecast(
            temperature = temperature,
            description = description,
            feelsLike = feelsLikeTemperature,
            wind = wind,
            pressure = pressure,
            humidity = humidity,
            imageId = imageId
        )
    }

    private fun responseToHourForecastList(response: HourForecastResponse): ArrayList<HourForecast> =
        response.list.map { forecast ->
            val temperature = forecast.main.temp.roundToInt().let { if (it > 0) "+$it" else "$it" }
            val time = forecast.dtTxt.substring(11, 16)
            val weatherCode = forecast.weather[0].id
            val dayPart = forecast.weather[0].icon.let { it[it.length - 1] }
            val imageId = ImageChooser.OpenWeatherMap.getImageIdHourForecast(weatherCode, dayPart)
            HourForecast(temperature = temperature, time = time, imageId = imageId)
        } as ArrayList
}