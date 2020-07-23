package com.enxy.weather.data.db

import androidx.room.*
import com.enxy.weather.data.entity.Forecast

@Dao
abstract class ForecastDao {
    @Query("SELECT NOT EXISTS(SELECT * FROM forecast)")
    abstract suspend fun isDatabaseEmpty(): Boolean

    @Query("SELECT EXISTS(SELECT 1 FROM forecast WHERE locationName = :locationName LIMIT 1)")
    abstract suspend fun isForecastSaved(locationName: String): Boolean

    @Query("SELECT * FROM forecast WHERE isFavourite = 1")
    abstract suspend fun getFavouriteForecastList(): List<Forecast>?

    @Query("SELECT * FROM forecast WHERE wasOpenedLast = 1 LIMIT 1")
    abstract suspend fun getLastOpenedForecast(): Forecast

    @Query("SELECT * FROM forecast WHERE locationName = :locationName LIMIT 1")
    abstract suspend fun getForecastByLocationName(locationName: String): Forecast

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insertForecast(forecast: Forecast)

    @Update
    abstract suspend fun updateForecast(forecast: Forecast)

    @Query("UPDATE forecast SET wasOpenedLast = CASE WHEN locationName = :locationName THEN 1 ELSE 0 END")
    abstract suspend fun updateLastOpenedForecast(locationName: String)

    @Query("UPDATE forecast SET isFavourite = :isFavourite WHERE locationName = :locationName")
    abstract suspend fun setForecastFavouriteStatus(
        locationName: String,
        isFavourite: Boolean
    )

    @Delete
    abstract suspend fun deleteForecast(forecast: Forecast)
}
