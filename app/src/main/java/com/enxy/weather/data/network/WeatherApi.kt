package com.enxy.weather.data.network

import com.enxy.weather.data.network.json.openweathermap.current.CurrentForecastResponse
import com.enxy.weather.data.network.json.openweathermap.hour.HourForecastResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApi {
    @GET("/data/2.5/forecast")
    suspend fun getHourForecastAsync(
        @Query("lon") longitude: Double,
        @Query("lat") latitude: Double,
        @Query("APPID") APPID: String,
        @Query("cnt") count: Int,
        @Query("lang") language: String,
        @Query("units") units: String
    ): Response<HourForecastResponse>

    @GET("/data/2.5/weather")
    suspend fun getCurrentForecastAsync(
        @Query("lon") longitude: Double,
        @Query("lat") latitude: Double,
        @Query("APPID") APPID: String,
        @Query("lang") language: String,
        @Query("units") units: String
    ): Response<CurrentForecastResponse>
}