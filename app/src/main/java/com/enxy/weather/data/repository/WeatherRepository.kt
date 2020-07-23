package com.enxy.weather.data.repository

import com.enxy.weather.BuildConfig
import com.enxy.weather.base.NetworkRepository
import com.enxy.weather.data.db.AppDataBase
import com.enxy.weather.data.entity.*
import com.enxy.weather.data.network.WeatherApi
import com.enxy.weather.data.network.getIconDarkSurface
import com.enxy.weather.data.network.getIconLightSurface
import com.enxy.weather.data.network.json.openweathermap.WeatherResponse
import com.enxy.weather.utils.Result
import com.enxy.weather.utils.Result.Error
import com.enxy.weather.utils.Result.Success
import com.enxy.weather.utils.exception.Failure
import com.enxy.weather.utils.extension.formatTo
import com.enxy.weather.utils.extension.toLocation
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.*
import kotlin.math.roundToInt

class WeatherRepository(
    private val weatherApi: WeatherApi,
    private val database: AppDataBase
) : NetworkRepository {
    companion object {
        // OpenWeatherMap API URL queries
        const val OPEN_WEATHER_MAP_APPID = BuildConfig.API_KEY_OPEN_WEATHER_MAP
        const val DEFAULT_LANGUAGE = "ENG"
        const val DEFAULT_UNITS = "metric"
        const val HOURLY_COUNT = 24
        const val DAILY_COUNT = 7
    }

    suspend fun getForecast(location: Location): Result<Failure, Forecast> {
        database.getForecastDao().updateLastOpenedForecast(location.locationName)
        val isForecastCached = database.getForecastDao().isForecastSaved(location.locationName)

        return if (isForecastCached) {
            val forecast: Forecast = database
                .getForecastDao()
                .getForecastByLocationName(location.locationName)

            if (!forecast.isOutdated()) {
                Success(forecast)
            } else {
                requestForecast(location).onSuccess {
                    database.getForecastDao().updateForecast(it)
                    it.isFavourite = forecast.isFavourite
                }
            }
        } else {
            requestForecast(location).onSuccess {
                database.getForecastDao().insertForecast(it)
            }
        }
    }

    suspend fun updateForecast(forecast: Forecast): Result<Failure, Forecast> =
        requestForecast(forecast.toLocation()).onSuccess {
            it.isFavourite = forecast.isFavourite
            database.getForecastDao().updateForecast(it)
        }


    suspend fun getLastOpenedForecast(): Result<Failure, Forecast> {
        return if (isDatabaseEmpty()) {
            Error(Failure.DataNotFound)
        } else {
            Success(database.getForecastDao().getLastOpenedForecast())
        }
    }

    suspend fun isDatabaseEmpty(): Boolean = database.getForecastDao().isDatabaseEmpty()

    suspend fun getFavouriteLocations(): Result<Failure, ArrayList<Location>> =
        withContext(Dispatchers.IO) {
            val favouriteForecasts = database.getForecastDao().getFavouriteForecastList()
            if (favouriteForecasts != null) {
                Success(favouriteForecasts.map { it.toLocation() } as ArrayList)
            } else {
                Error(Failure.DataNotFound)
            }
        }

    suspend fun changeForecastFavouriteStatus(forecast: Forecast, isFavourite: Boolean) {
        database.getForecastDao()
            .setForecastFavouriteStatus(forecast.locationName, isFavourite)
    }

    private suspend fun requestForecast(location: Location): Result<Failure, Forecast> =
        safeApiCall(
            call = {
                weatherApi.getForecast(
                    longitude = location.longitude,
                    latitude = location.latitude,
                    APPID = OPEN_WEATHER_MAP_APPID,
                    language = DEFAULT_LANGUAGE,
                    units = DEFAULT_UNITS
                )
            },
            transform = ::responseToForecast
        ).onSuccess { it.locationName = location.locationName }

    private fun responseToForecast(response: WeatherResponse) = Forecast(
        locationName = response.timezone, // temporary name that will be changed
        longitude = response.lon,
        latitude = response.lat,
        currentForecast = CurrentForecast(
            temperature = response.current.temp.roundToInt(),
            feelsLike = response.current.feelsLike.roundToInt(),
            description = response.current.weather[0].description.capitalize(),
            wind = response.current.windSpeed.roundToInt(),
            humidity = response.current.humidity,
            pressure = response.current.pressure,
            imageId = getIconLightSurface(
                response.current.weather[0].id,
                response.current.weather[0].icon.last()
            )
        ),
        hourForecastList = response.hourly
            .take(HOURLY_COUNT)
            .map { hourly ->
                HourForecast(
                    temperature = hourly.temperature.roundToInt(),
                    time = hourly.timestamp formatTo "HH:mm",
                    imageId = getIconDarkSurface(
                        hourly.weather[0].id,
                        hourly.weather[0].icon.last()
                    )
                )
            } as ArrayList,
        dayForecastList = response.daily
            .take(DAILY_COUNT)
            .map { daily ->
                DayForecast(
                    highestTemp = daily.temperature.max.roundToInt(),
                    lowestTemp = daily.temperature.min.roundToInt(),
                    day = (daily.timestamp formatTo "EEEE").capitalize(),
                    date = daily.timestamp formatTo "dd.MM",
                    imageId = getIconLightSurface(
                        daily.weather[0].id,
                        daily.weather[0].icon.last()
                    )
                )
            } as ArrayList
    )
}