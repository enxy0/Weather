package com.enxy.weather.data.location

import com.enxy.weather.base.NetworkDataSource
import com.enxy.weather.data.entity.Location
import com.enxy.weather.utils.Result

interface LocationDataSource : NetworkDataSource {
    suspend fun request(locationName: String): Result<List<Location>>
}