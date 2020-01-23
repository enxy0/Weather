package com.enxy.weather.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NetworkService @Inject constructor() {
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

    fun weatherApi(): Api = openWeatherMapRetrofit.create(Api::class.java)

    fun locationApi(): CityApi = openCageRetrofit.create(CityApi::class.java)
}