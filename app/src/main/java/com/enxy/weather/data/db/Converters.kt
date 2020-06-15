package com.enxy.weather.data.db

import androidx.room.TypeConverter
import com.enxy.weather.data.entity.HourForecast
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type
import java.util.*

class Converters {
    val gson = Gson()

    @TypeConverter fun stringToHourList(value: String): ArrayList<HourForecast> {
        val listType: Type = object : TypeToken<List<HourForecast>>() {}.type
        return gson.fromJson<ArrayList<HourForecast>>(value, listType)
    }

    @TypeConverter fun hourListToString(hourForecastList: List<HourForecast>): String {
        return gson.toJson(hourForecastList)
    }

    @TypeConverter fun calendarToLong(calendar: Calendar): Long = calendar.timeInMillis

    @TypeConverter fun longToCalendar(value: Long): Calendar =
        Calendar.getInstance().apply { timeInMillis = value }
}