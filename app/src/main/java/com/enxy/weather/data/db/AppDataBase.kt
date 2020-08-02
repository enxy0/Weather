package com.enxy.weather.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.enxy.weather.data.entity.CurrentForecast
import com.enxy.weather.data.entity.Forecast

@Database(entities = [CurrentForecast::class, Forecast::class], version = 4)
@TypeConverters(Converters::class)
abstract class AppDataBase : RoomDatabase() {
    abstract fun getForecastDao(): ForecastDao

    companion object {
        const val DATABASE_NAME = "weather-db"
    }
}