package com.enxy.weather.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [CurrentForecast::class, HourForecast::class], version = 1)
@TypeConverters(HourListConverter::class)
abstract class AppDataBase : RoomDatabase() {
    abstract fun getHourForecastDao(): HourForecastDao
    abstract fun getCurrentForecastDao(): CurrentForecastDao

    companion object {
        const val DATABASE_NAME = "weather-db"

        @Volatile private var instance: AppDataBase? = null

        fun getInstance(context: Context): AppDataBase {
            return instance ?: synchronized(this) {
                instance ?: Room.databaseBuilder(context, AppDataBase::class.java, DATABASE_NAME)
                    .build()
            }
        }
    }
}