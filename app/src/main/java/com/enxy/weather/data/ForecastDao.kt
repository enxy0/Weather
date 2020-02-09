package com.enxy.weather.data

import androidx.room.*

@Dao
abstract class ForecastDao {
    @Query("SELECT EXISTS(SELECT * FROM forecast)")
    abstract suspend fun hasCachedForecasts(): Boolean

    @Query("SELECT EXISTS(SELECT 1 FROM forecast WHERE longitude = :longitude AND latitude = :latitude LIMIT 1)")
    abstract suspend fun isForecastCached(longitude: Double, latitude: Double): Boolean

    @Query("SELECT * FROM forecast WHERE wasOpenedLast = 1 LIMIT 1")
    abstract suspend fun getLastOpenedForecast(): Forecast

    @Query("SELECT * FROM forecast WHERE longitude = :longitude AND latitude = :latitude LIMIT 1")
    abstract suspend fun getForecastByLocationName(longitude: Double, latitude: Double): Forecast

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insertForecast(forecast: Forecast)

    @Query("UPDATE forecast SET wasOpenedLast = CASE WHEN longitude = :longitude AND latitude = :latitude THEN 1 ELSE 0 END")
    abstract suspend fun updateLastOpenedForecast(longitude: Double, latitude: Double)

    @Delete
    abstract suspend fun deleteForecast(forecast: Forecast)

    @Query("DELETE FROM forecast WHERE longitude = :longitude AND latitude = :latitude")
    abstract suspend fun deleteForecastByLocation(longitude: Double, latitude: Double)

    @Query("DELETE FROM forecast")
    abstract suspend fun deleteAllForecasts()
}
