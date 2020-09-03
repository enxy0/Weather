package com.enxy.weather.data.weather

import com.enxy.weather.data.AppSettings
import com.enxy.weather.data.db.AppDataBase
import com.enxy.weather.data.entity.FavouriteForecast
import com.enxy.weather.data.entity.Forecast
import com.enxy.weather.data.entity.Location
import com.enxy.weather.utils.Result
import com.enxy.weather.utils.Result.Error
import com.enxy.weather.utils.Result.Success
import com.enxy.weather.utils.exception.DataNotFound
import com.enxy.weather.utils.extension.toFavouriteForecast
import com.enxy.weather.utils.extension.toLocation
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class OpenWeatherMapRepository(
    private val weatherDataSource: WeatherDataSource,
    private val database: AppDataBase,
    private val settings: AppSettings
) : WeatherRepository {

    override suspend fun getForecastById(id: Int): Result<Forecast> =
        withContext(Dispatchers.Default) {
            // Since id is a parameter, forecast must be in database
            val forecast: Forecast = database.getForecastDao().getForecasts().find { it.id == id }!!

            // Changing displayed (current) forecast on the main screen
            database.getForecastDao().updateCurrentForecast(forecast.locationName)

            // If data is outdated, then requesting new forecast
            if (forecast.isOutdated) {
                return@withContext weatherDataSource
                    .request(forecast.toLocation(), settings.locale)
                    .onSuccess {
                        it.isFavourite = forecast.isFavourite
                        it.id = forecast.id
                        database.getForecastDao().updateForecast(it)
                    }
            }
            return@withContext Success(forecast)
        }

    override suspend fun getForecastByLocation(location: Location): Result<Forecast> =
        withContext(Dispatchers.Default) {
            // Forecast can be null if user chose new location (in search)
            val forecast: Forecast? = database.getForecastDao().getForecasts()
                .find { it.locationName == location.locationName }

            // Changing displayed (current) forecast on the main screen
            database.getForecastDao().updateCurrentForecast(location.locationName)

            if (forecast != null) {
                if (!forecast.isOutdated) {
                    return@withContext Success(forecast)
                } else {
                    return@withContext getUpdatedForecast(forecast)
                }
            } else {
                return@withContext weatherDataSource
                    .request(location, settings.locale)
                    .onSuccess { database.getForecastDao().insertForecast(it) }
            }
        }

    /**
     * Returns result of requesting new forecast by given old [forecast] information
     */
    override suspend fun getUpdatedForecast(forecast: Forecast): Result<Forecast> {
        return weatherDataSource
            .request(forecast.toLocation(), settings.locale)
            .onSuccess {
                it.id = forecast.id
                it.isFavourite = forecast.isFavourite
                database.getForecastDao().updateForecast(it)
            }
    }

    /**
     * Observable result of getting current forecast
     *
     * At first sends cached forecast, and then if it is outdated sends updated one
     */
    override suspend fun getObservableCurrentForecast(): Flow<Result<Forecast>> = flow {
        val currentForecast = database.getForecastDao().getCurrentForecast()
        if (currentForecast != null) {
            emit(Success(currentForecast))
            if (currentForecast.isOutdated) {
                emit(getUpdatedForecast(currentForecast))
            }
        } else {
            emit(Error(DataNotFound))
        }
    }

    /**
     * Checks if database contains any data
     */
    override suspend fun isEmpty(): Boolean = database.getForecastDao().getForecasts().isEmpty()

    /**
     *  Returns result of getting favourite locations from database
     */
    override suspend fun getFavouriteForecasts(): Result<List<FavouriteForecast>> =
        withContext(Dispatchers.Default) {
            val favouriteForecasts = database.getForecastDao().getFavouriteForecasts()
            return@withContext if (favouriteForecasts.isNotEmpty()) {
                Success(favouriteForecasts.map(Forecast::toFavouriteForecast))
            } else {
                Error(DataNotFound)
            }
        }

    /**
     * Updates forecast for favourite locations asynchronously
     */
    override suspend fun updateFavouriteForecasts() = coroutineScope {
        val favouriteForecasts: List<Forecast> = database.getForecastDao().getFavouriteForecasts()
        for (oldForecast in favouriteForecasts) {
            if (oldForecast.isOutdated) {
                launch {
                    getUpdatedForecast(oldForecast)
                }
            }
        }
    }

    /**
     * Changes forecast favourite status (isFavourite) in database
     */
    override suspend fun starEvent(forecast: Forecast, isStarred: Boolean) {
        database.getForecastDao().setForecastFavouriteStatus(forecast.locationName, isStarred)
    }
}