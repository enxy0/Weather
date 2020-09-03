package com.enxy.weather.data.weather

import com.enxy.weather.base.NetworkDataSource
import com.enxy.weather.data.entity.Forecast
import com.enxy.weather.data.entity.Location
import com.enxy.weather.utils.Result
import java.util.*

interface WeatherDataSource : NetworkDataSource {
    /**
     * Performs GET request to the OpenWeatherMap API to fetch new [Forecast]
     */
    suspend fun request(location: Location, locale: Locale): Result<Forecast>
}