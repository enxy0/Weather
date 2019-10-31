package com.enxy.weather.network

import com.enxy.weather.network.json.current.CurrentWeatherResponse
import com.enxy.weather.network.json.hour.HourWeatherResponse
import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface Api {
    @GET("/data/2.5/{type}")
    fun getHourWeatherAsync(
        @Path("type") type: String,
        @Query("APPID") APPID: String,
        @Query("id") id: String,
        @Query("cnt") count: Int,
        @Query("lang") language: String,
        @Query("units") units: String
    ): Deferred<Response<HourWeatherResponse>>

    @GET("/data/2.5/{type}")
    fun getCurrentWeatherAsync(
        @Path("type") type: String,
        @Query("APPID") APPID: String,
        @Query("id") id: String,
        @Query("cnt") count: Int,
        @Query("lang") language: String,
        @Query("units") units: String
    ): Deferred<Response<CurrentWeatherResponse>>
}