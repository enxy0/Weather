package com.enxy.weather.data

import androidx.room.*

@Dao
abstract class CurrentForecastDao {
    @Query("SELECT * FROM current_weather")
    abstract fun getCurrentWeather(): CurrentForecast

    @Query("SELECT EXISTS(SELECT 1 FROM current_weather where (longitude = :longitude AND latitude = :latitude) LIMIT 1)")
    abstract fun isCached(longitude: Double, latitude: Double): Boolean

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertCurrentForecast(currentForecast: CurrentForecast)

    @Delete
    abstract suspend fun deleteCurrentForecast(currentForecast: CurrentForecast)

    @Transaction
    open suspend fun updateData(currentForecast: CurrentForecast) {
        deleteAllCurrentForecasts()
        insertCurrentForecast(currentForecast)
    }

    @Query("DELETE FROM current_weather")
    abstract suspend fun deleteAllCurrentForecasts()
}