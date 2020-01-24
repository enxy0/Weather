package com.enxy.weather.ui.search

import com.enxy.weather.BuildConfig
import com.enxy.weather.base.BaseRepository
import com.enxy.weather.exception.Failure
import com.enxy.weather.functional.Result
import com.enxy.weather.network.NetworkService
import com.enxy.weather.network.json.opencage.LocationResponse
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LocationRepository @Inject constructor(private val service: NetworkService) :
    BaseRepository() {
    companion object {
        const val OPEN_CAGE_API_KEY = BuildConfig.API_KEY_OPEN_CAGE
    }

    suspend fun getLocationsByName(locationName: String): Result<Failure, ArrayList<LocationInfo>> {
        return safeApiCall(
            call = {
                service.locationApi().getLocationsByNameAsync(
                    locationName,
                    OPEN_CAGE_API_KEY
                )
            },
            transform = ::transformLocationResponse
        )
    }

    private fun transformLocationResponse(locationResponse: LocationResponse): ArrayList<LocationInfo> {
        val data = ArrayList<LocationInfo>()
        for (result in locationResponse.results) {
            val latitude = result.geometry.lat
            val longitude = result.geometry.lng
            val formattedLocationName = result.formatted
            val confidence = result.confidence
            val model = LocationInfo(longitude, latitude, formattedLocationName, confidence)
            data.add(model)
        }
        return data
    }
}