package com.enxy.weather.data.db

import androidx.room.TypeConverter
import com.enxy.weather.data.entity.DayForecast
import com.enxy.weather.data.entity.HourForecast
import com.enxy.weather.data.entity.Units.*
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type
import java.util.*

/**
 * Type converters to allow Room to reference complex data types.
 */
class Converters {
    val gson = Gson()

    @TypeConverter fun stringToHourList(value: String): ArrayList<HourForecast> {
        val listType: Type = object : TypeToken<List<HourForecast>>() {}.type
        return gson.fromJson<ArrayList<HourForecast>>(value, listType)
    }

    @TypeConverter fun stringToDayList(value: String): ArrayList<DayForecast> {
        val listType: Type = object : TypeToken<List<DayForecast>>() {}.type
        return gson.fromJson<ArrayList<DayForecast>>(value, listType)
    }

    @TypeConverter fun hourListToString(hourForecastList: List<HourForecast>): String {
        return gson.toJson(hourForecastList)
    }

    @TypeConverter fun dayListToString(dayForecastList: List<DayForecast>): String {
        return gson.toJson(dayForecastList)
    }

    @TypeConverter fun calendarToLong(calendar: Calendar): Long = calendar.timeInMillis

    @TypeConverter fun longToCalendar(value: Long): Calendar =
        Calendar.getInstance().apply { timeInMillis = value }

    @TypeConverter fun temperatureToInt(temperature: Temperature) = temperature.raw

    @TypeConverter fun windToInt(wind: Wind) = wind.raw

    @TypeConverter fun pressureToInt(pressure: Pressure) = pressure.raw

    @TypeConverter fun intToTemperature(value: Int) = Temperature(value)

    @TypeConverter fun intToWind(value: Int) = Wind(value)

    @TypeConverter fun intToPressure(value: Int) = Pressure(value)
}