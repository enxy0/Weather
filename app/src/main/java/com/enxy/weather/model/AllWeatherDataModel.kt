package com.enxy.weather.model

import com.google.gson.annotations.SerializedName

class AllWeatherDataModel(
    @SerializedName("currentWeatherModel")
    val currentWeatherModel: CurrentWeatherModel,
    @SerializedName("hourDataModelList")
    val hourWeatherModelArrayList: ArrayList<HourWeatherModel>,
    @SerializedName("dayDataList")
    val dayWeatherModelArrayList: ArrayList<DayWeatherModel>
)
