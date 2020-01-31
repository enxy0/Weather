package com.enxy.weather.data

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type

class HourListConverter {
    val gson = Gson()

    @TypeConverter fun stringToHourList(hourString: String): ArrayList<Hour> {
        val listType: Type = object : TypeToken<List<Hour>>() {}.type
        return gson.fromJson<ArrayList<Hour>>(hourString, listType)
    }

    @TypeConverter fun hourListToString(hourList: List<Hour>): String {
        return gson.toJson(hourList)
    }
}