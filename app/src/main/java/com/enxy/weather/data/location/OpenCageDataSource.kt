package com.enxy.weather.data.location

import com.enxy.weather.BuildConfig
import com.enxy.weather.data.entity.Location
import com.enxy.weather.data.network.LocationApi
import com.enxy.weather.data.network.json.opencage.LocationResponse
import com.enxy.weather.utils.Result

class OpenCageDataSource(private val locationApi: LocationApi) :
    LocationDataSource {
    companion object {
        const val OPEN_CAGE_API_KEY = BuildConfig.API_KEY_OPEN_CAGE
    }

    /**
     * Performs GET request to the OpenCage API to fetch locations by [locationName].
     */
    override suspend fun request(locationName: String): Result<List<Location>> {
        return safeApiCall(
            call = { locationApi.getLocationsByNameAsync(locationName, OPEN_CAGE_API_KEY) },
            params = Unit,
            transform = ::transformToLocationList
        )
    }

    /**
     * Converts [LocationResponse] to the list of [Location]
     */
    private fun transformToLocationList(
        locationResponse: LocationResponse,
        params: Unit
    ): List<Location> {
        return locationResponse.results.map {
            Location(it.formatted, it.geometry.lng, it.geometry.lat)
        }
    }
}