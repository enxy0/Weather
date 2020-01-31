package com.enxy.weather.data

import androidx.room.*

@Dao
abstract class HourForecastDao {
    @Query("SELECT * FROM hour_weather")
    abstract fun getHourForecast(): HourForecast

    @Query("SELECT EXISTS(SELECT 1 FROM hour_weather where (longitude = :longitude AND latitude = :latitude) LIMIT 1)")
    abstract fun isCached(longitude: Double, latitude: Double): Boolean

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insertHourForecast(hourForecast: HourForecast)

    @Transaction
    open suspend fun updateData(hourForecast: HourForecast) {
        deleteAllHourForecasts()
        insertHourForecast(hourForecast)
    }

    @Query("DELETE FROM hour_weather")
    abstract suspend fun deleteAllHourForecasts()

    @Delete
    abstract suspend fun deleteHourForecast(hourForecast: HourForecast)
}