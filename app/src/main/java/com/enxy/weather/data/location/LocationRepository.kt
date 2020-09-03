package com.enxy.weather.data.location

import com.enxy.weather.data.entity.Location
import com.enxy.weather.utils.Result

interface LocationRepository {
    /**
     * Performs GET request to the OpenCage API to fetch locations
     */
    suspend fun getLocationsByName(locationName: String): Result<List<Location>>
}