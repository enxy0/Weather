package com.enxy.weather.model

import com.google.gson.annotations.SerializedName

class AllWeatherDataModel(
    @SerializedName("currentDataModel")
    val currentDataModel: CurrentDataModel,
    @SerializedName("hourDataModelList")
    val hourDataModelArrayList: ArrayList<HourDataModel>,
    @SerializedName("dayDataList")
    val dayDataModelArrayList: ArrayList<DayDataModel>
)
