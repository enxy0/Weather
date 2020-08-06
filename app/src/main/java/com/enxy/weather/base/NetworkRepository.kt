package com.enxy.weather.base

import com.enxy.weather.utils.Result
import com.enxy.weather.utils.Result.Error
import com.enxy.weather.utils.Result.Success
import com.enxy.weather.utils.exception.BadServerResponse
import com.enxy.weather.utils.exception.NoConnection
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response
import java.io.IOException

interface NetworkRepository {
    suspend fun <JSON, MODEL> safeApiCall(
        call: suspend () -> Response<JSON>,
        transform: (JSON) -> MODEL
    ): Result<MODEL> {
        try {
            val response = withContext(Dispatchers.IO) { call.invoke() }
            return if (response.isSuccessful) {
                val body = response.body()
                if (body != null) {
                    withContext(Dispatchers.Default) {
                        Success(transform(body))
                    }
                } else {
                    Error(BadServerResponse)
                }
            } else {
                Error(BadServerResponse)
            }
        } catch (ioException: IOException) {
            ioException.printStackTrace()
            return Error(NoConnection)
        } catch (unknown: Exception) {
            unknown.printStackTrace()
            return Error(BadServerResponse)
        }
    }
}