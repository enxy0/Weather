package com.enxy.weather.ui.main

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.enxy.weather.model.AllWeatherDataModel
import com.enxy.weather.model.CurrentDataModel
import com.enxy.weather.model.DayDataModel
import com.enxy.weather.model.HourDataModel


class MainViewModel(application: Application) : AndroidViewModel(application) {
    val allWeatherDataModel = MutableLiveData<AllWeatherDataModel>()

    init {
        loadAllWeatherData()
    }

    fun loadAllWeatherData() {
        allWeatherDataModel.value = AllWeatherDataModel(
            getCurrentDataModel(),
            getHourWeatherArrayList(),
            getDayWeatherArrayList()
        )
    }

    fun getCurrentDataModel() = CurrentDataModel(
        17,
        2,
        "Sunny",
        "Monday",
        "23:30",
        1016,
        85,
        "weather_clear_day",
        15
    )

    fun getHourWeatherArrayList() = arrayListOf(
        HourDataModel(
            "+17",
            "00:00",
            "weather_clear_night"
        ),
        HourDataModel(
            "+15",
            "01:00",
            "weather_night_cloudy_rain_light"
        ),
        HourDataModel(
            "+13",
            "02:00",
            "weather_thunderstorm_rain_middle"
        ),
        HourDataModel(
            "+14",
            "03:00",
            "weather_thunderstorm_rain_heavy"
        ),
        HourDataModel(
            "+13",
            "04:00",
            "weather_broken_clouds"
        ),
        HourDataModel(
            "+16",
            "05:00",
            "weather_rain_light"
        ),
        HourDataModel(
            "+16",
            "06:00",
            "weather_snow_light"
        ),
        HourDataModel(
            "+17",
            "07:00",
            "weather_snow_middle"
        ),
        HourDataModel(
            "+18",
            "08:00",
            "weather_snow_heavy"
        ),
        HourDataModel(
            "+19",
            "09:00",
            "weather_scattered_clouds"
        ),
        HourDataModel(
            "+21",
            "10:00",
            "weather_mist"
        ),
        HourDataModel(
            "+23",
            "11:00",
            "weather_night_cloudy"
        ),
        HourDataModel(
            "+23",
            "11:00",
            "weather_day_cloudy"
        )
    )

    fun getDayWeatherArrayList() = arrayListOf(
        DayDataModel(
            "Monday",
            "+17",
            "+12",
            "07.09.2019",
            "weather_thunderstorm_rain_heavy"
        ),
        DayDataModel(
            "Tuesday",
            "+16",
            "+13",
            "08.09.2019",
            "weather_day_cloudy"
        ),
        DayDataModel(
            "Wednesday",
            "+13",
            "+10",
            "09.09.2019",
            "weather_clear_day"
        ),
        DayDataModel(
            "Thursday",
            "+10",
            "+7",
            "10.09.2019",
            "weather_snow_heavy"
        ),
        DayDataModel(
            "Friday",
            "+14",
            "+10",
            "11.09.2019",
            "weather_scattered_clouds"
        )
    )
}
