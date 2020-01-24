package com.enxy.weather.network

import com.enxy.weather.network.json.opencage.LocationResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface CityApi {
    @GET("/geocode/v1/json")
    suspend fun getLocationsByNameAsync(
        @Query("q", encoded = true) locationName: String,
        @Query("language") language: String,
        @Query("key") apiKey: String
    ): Response<LocationResponse>
}