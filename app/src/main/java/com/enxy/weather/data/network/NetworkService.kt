package com.enxy.weather.data.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class NetworkService {
    private val openWeatherMapRetrofit: Retrofit = Retrofit.Builder()
        .baseUrl(OPEN_WEATHER_MAP_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val openCageRetrofit: Retrofit = Retrofit.Builder()
        .baseUrl(OPEN_CAGE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    companion object {
        const val OPEN_WEATHER_MAP_URL = "https://api.openweathermap.org"
        const val OPEN_CAGE_URL = "https://api.opencagedata.com/"
    }

    fun weatherApi(): WeatherApi = openWeatherMapRetrofit.create(WeatherApi::class.java)

    fun locationApi(): LocationApi = openCageRetrofit.create(LocationApi::class.java)
}