package com.enxy.weather.data.repository

import com.enxy.weather.BuildConfig
import com.enxy.weather.base.NetworkRepository
import com.enxy.weather.data.db.AppDataBase
import com.enxy.weather.data.entity.*
import com.enxy.weather.data.entity.Units.*
import com.enxy.weather.data.network.WeatherApi
import com.enxy.weather.data.network.getIconDarkSurface
import com.enxy.weather.data.network.getIconLightSurface
import com.enxy.weather.data.network.json.openweathermap.WeatherResponse
import com.enxy.weather.utils.Result
import com.enxy.weather.utils.Result.Error
import com.enxy.weather.utils.Result.Success
import com.enxy.weather.utils.exception.DataNotFound
import com.enxy.weather.utils.extension.formatTo
import com.enxy.weather.utils.extension.toLocation
import com.enxy.weather.utils.extension.toMiniForecast
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

    /**
     * Returns result of retrieving forecast by given id
     * Result.Success<Forecast> -> displayed (current) forecast successfully changed
     * Result.Error -> error happened while retrieving forecast
     */
    suspend fun getForecastById(id: Int): Result<Forecast> {
        // Since id is a parameter, forecast must be in database
        val forecast: Forecast = database.getForecastDao().getForecasts().find { it.id == id }!!

        // Changing displayed (current) forecast on the main screen
        database.getForecastDao().updateCurrentForecast(forecast.locationName)

        // If data is outdated, then requesting new forecast
        if (forecast.isOutdated()) {
            return requestForecast(forecast.toLocation()).onSuccess {
                it.isFavourite = forecast.isFavourite
                it.id = forecast.id
                database.getForecastDao().updateForecast(it)
            }
        }
        return Success(forecast)
    }

    /**
     * Returns result of retrieving forecast by given location
     * Result.Success<Forecast> -> displayed (current) forecast successfully changed
     * Result.Error -> error happened while retrieving forecast
     */
    suspend fun getForecastByLocation(location: Location): Result<Forecast> {
        // Forecast can be null if user chose new location (in search)
        val forecast: Forecast? = database.getForecastDao().getForecasts()
            .find { it.locationName == location.locationName }

        // Changing displayed (current) forecast on the main screen
        database.getForecastDao().updateCurrentForecast(location.locationName)

        return if (forecast != null) {
            if (!forecast.isOutdated()) {
                Success(forecast)
            } else {
                getUpdatedForecast(forecast)
            }
        } else {
            requestForecast(location).onSuccess {
                database.getForecastDao().insertForecast(it)
            }
        }
    }

    /**
     * Returns result of requesting new forecast by given old [forecast] information
     */
    suspend fun getUpdatedForecast(forecast: Forecast): Result<Forecast> =
        requestForecast(forecast.toLocation()).onSuccess {
            it.id = forecast.id
            it.isFavourite = forecast.isFavourite
            database.getForecastDao().updateForecast(it)
        }

    /**
     * Returns observable displayed (current) forecast
     */
    suspend fun getCurrentForecast(): Result<Forecast> {
        val currentForecast = database.getForecastDao().getCurrentForecast()
        return if (currentForecast != null)
            Success(currentForecast)
        else
            Error(DataNotFound)
    }

    suspend fun isDatabaseEmpty(): Boolean = database.getForecastDao().getForecasts().isEmpty()

    suspend fun getFavouriteLocations(): Result<List<MiniForecast>> {
        val favouriteForecasts = database.getForecastDao().getFavouriteForecasts()
        return if (favouriteForecasts.isNotEmpty()) {
            withContext(Dispatchers.Default) {
                Success(favouriteForecasts.map(Forecast::toMiniForecast))
            }
        } else {
            Error(DataNotFound)
        }
    }

    suspend fun changeForecastFavouriteStatus(forecast: Forecast, isFavourite: Boolean) {
        database.getForecastDao()
            .setForecastFavouriteStatus(forecast.locationName, isFavourite)
    }

    private suspend fun requestForecast(location: Location): Result<Forecast> =
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
            temperature = Temperature(response.current.temp.roundToInt()),
            feelsLike = Temperature(response.current.feelsLike.roundToInt()),
            description = response.current.weather[0].description.capitalize(),
            wind = Wind(response.current.windSpeed.roundToInt()),
            humidity = response.current.humidity,
            pressure = Pressure(response.current.pressure),
            imageId = getIconLightSurface(
                response.current.weather[0].id,
                response.current.weather[0].icon.last()
            )
        ),
        hourForecastList = response.hourly
            .take(HOURLY_COUNT)
            .map { hourly ->
                HourForecast(
                    temperature = Temperature(hourly.temperature.roundToInt()),
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
                    highestTemp = Temperature(daily.temperature.max.roundToInt()),
                    lowestTemp = Temperature(daily.temperature.min.roundToInt()),
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