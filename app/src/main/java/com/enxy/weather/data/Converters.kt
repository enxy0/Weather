package com.enxy.weather.data

import androidx.room.TypeConverter
import com.enxy.weather.data.entity.Hour
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type
import java.util.*

class Converters {
    val gson = Gson()

    @TypeConverter fun stringToHourList(value: String): ArrayList<Hour> {
        val listType: Type = object : TypeToken<List<Hour>>() {}.type
        return gson.fromJson<ArrayList<Hour>>(value, listType)
    }

    @TypeConverter fun hourListToString(hourList: List<Hour>): String {
        return gson.toJson(hourList)
    }

    @TypeConverter fun calendarToLong(calendar: Calendar): Long = calendar.timeInMillis

    @TypeConverter fun longToCalendar(value: Long): Calendar =
        Calendar.getInstance().apply { timeInMillis = value }
}