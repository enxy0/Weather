package com.enxy.weather.network

import retrofit2.Retrofit
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NetworkService @Inject constructor(private val retrofit: Retrofit) {
    companion object {
        const val BASE_URL = "https://api.openweathermap.org"
    }

    fun weatherApi(): Api = retrofit.create(Api::class.java)
}