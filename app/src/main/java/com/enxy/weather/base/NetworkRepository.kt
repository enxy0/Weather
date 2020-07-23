package com.enxy.weather.base

import android.util.Log
import com.enxy.weather.utils.Result
import com.enxy.weather.utils.Result.Error
import com.enxy.weather.utils.Result.Success
import com.enxy.weather.utils.exception.Failure
import com.enxy.weather.utils.exception.Failure.BadServerResponse
import com.enxy.weather.utils.exception.Failure.NoConnection
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response
import java.io.IOException


interface NetworkRepository {
    suspend fun <JSON, MODEL> safeApiCall(
        call: suspend () -> Response<JSON>,
        transform: (JSON) -> MODEL
    ): Result<Failure, MODEL> {
        return try {
            val response = withContext(Dispatchers.IO) { call.invoke() }
            when (response.isSuccessful) {
                true -> {
                    val body = response.body()
                    if (body != null) {
                        withContext(Dispatchers.Default) {
                            Success(transform(body))
                        }
                    } else {
                        Error(BadServerResponse)
                    }
                }
                false -> {
                    Error(BadServerResponse)
                }
            }
        } catch (e: IOException) {
            Log.e("NetworkRepository", "safeApiCall:", e)
            Error(NoConnection)
        } catch (unknownThrowable: Throwable) {
            Log.e("NetworkRepository", "safeApiCall:", unknownThrowable)
            Error(BadServerResponse)
        }
    }
}