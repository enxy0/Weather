package com.enxy.weather.data.db

import androidx.room.TypeConverter
import com.enxy.weather.data.entity.*
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type
import java.util.*

/**
 * Type converters to allow Room to reference complex data types.
 */
class Converters {
    val gson = Gson()

    @TypeConverter fun stringToHourList(value: String): List<HourForecast> {
        val listType: Type = object : TypeToken<List<HourForecast>>() {}.type
        return gson.fromJson<List<HourForecast>>(value, listType)
    }

    @TypeConverter fun stringToDayList(value: String): List<DayForecast> {
        val listType: Type = object : TypeToken<List<DayForecast>>() {}.type
        return gson.fromJson<List<DayForecast>>(value, listType)
    }

    @TypeConverter fun hourListToString(hourForecastList: List<HourForecast>): String {
        return gson.toJson(hourForecastList)
    }

    @TypeConverter fun dayListToString(dayForecastList: List<DayForecast>): String {
        return gson.toJson(dayForecastList)
    }

    @TypeConverter fun calendarToLong(calendar: Calendar): Long {
        return calendar.timeInMillis
    }

    @TypeConverter fun longToCalendar(value: Long): Calendar {
        return Calendar.getInstance().apply { timeInMillis = value }
    }

    @TypeConverter fun temperatureToInt(temperature: Temperature): Int {
        return temperature.raw
    }

    @TypeConverter fun windToInt(wind: Wind): Int {
        return wind.raw
    }

    @TypeConverter fun pressureToInt(pressure: Pressure): Int {
        return pressure.raw
    }

    @TypeConverter fun intToTemperature(value: Int): Temperature {
        return Temperature(value)
    }

    @TypeConverter fun intToWind(value: Int): Wind {
        return Wind(value)
    }

    @TypeConverter fun intToPressure(value: Int): Pressure {
        return Pressure(value)
    }
}