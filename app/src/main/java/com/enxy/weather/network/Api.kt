package com.enxy.weather.network

import com.enxy.weather.network.json.openweathermap.current.CurrentWeatherResponse
import com.enxy.weather.network.json.openweathermap.hour.HourWeatherResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface Api {
    @GET("/data/2.5/{type}")
    suspend fun getHourWeatherAsync(
        @Path("type") type: String,
        @Query("APPID") APPID: String,
        @Query("lon") longitude: Double,
        @Query("lat") latitude: Double,
        @Query("cnt") count: Int,
        @Query("lang") language: String,
        @Query("units") units: String
    ): Response<HourWeatherResponse>

    @GET("/data/2.5/{type}")
    suspend fun getCurrentWeatherAsync(
        @Path("type") type: String,
        @Query("APPID") APPID: String,
        @Query("lon") longitude: Double,
        @Query("lat") latitude: Double,
        @Query("cnt") count: Int,
        @Query("lang") language: String,
        @Query("units") units: String
    ): Response<CurrentWeatherResponse>
}