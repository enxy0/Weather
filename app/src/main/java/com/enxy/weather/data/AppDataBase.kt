package com.enxy.weather.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.enxy.weather.data.model.CurrentForecast
import com.enxy.weather.data.model.Forecast
import com.enxy.weather.data.model.HourForecast

@Database(entities = [CurrentForecast::class, HourForecast::class, Forecast::class], version = 2)
@TypeConverters(Converters::class)
abstract class AppDataBase : RoomDatabase() {
    abstract fun getForecastDao(): ForecastDao

    companion object {
        @JvmField
        val MIGRATION_1_2 = object : Migration(1, 2) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("ALTER TABLE forecast ADD COLUMN isFavourite INTEGER NOT NULL DEFAULT 0")
                database.execSQL("UPDATE forecast SET isFavourite = 0")
            }
        }
        const val DATABASE_NAME = "weather-db"

        @Volatile private var instance: AppDataBase? = null

        fun getInstance(context: Context): AppDataBase {
            return instance ?: synchronized(this) {
                instance ?: Room.databaseBuilder(context, AppDataBase::class.java, DATABASE_NAME)
                    .addMigrations(MIGRATION_1_2)
                    .build()
            }
        }
    }
}