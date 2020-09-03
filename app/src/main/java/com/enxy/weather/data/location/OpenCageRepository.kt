package com.enxy.weather.data.location

import com.enxy.weather.data.entity.Location
import com.enxy.weather.utils.Result

class OpenCageRepository(
    private val locationDataSource: LocationDataSource
): LocationRepository {
    override suspend fun getLocationsByName(locationName: String): Result<List<Location>> {
        return locationDataSource.request(locationName)
    }
}