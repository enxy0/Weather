package com.enxy.weather.base

import android.util.Log
import com.enxy.weather.exception.Failure
import com.enxy.weather.functional.Result
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response


open class BaseRepository {
    suspend fun <JSON, MODEL> safeApiCall(
        call: () -> Deferred<Response<JSON>>,
        transform: (JSON) -> MODEL
    ): Result<Failure, MODEL> = withContext(Dispatchers.Main) {
        try {
            val response = call().await()
            if (response.isSuccessful) {
                val body = response.body()
                if (body != null) {
                    withContext(Dispatchers.IO) {
                        val model = transform(body)
                        withContext(Dispatchers.Main) {
                            Result.Success(model)
                        }
                    }
                } else
                    Result.Error(Failure.ServerError)
            } else {
                Result.Error(Failure.ServerResponseError)
            }
        } catch (e: Throwable) {
            Log.e("BaseRepository", "safeApiCall: Exception: ${e.message} & ${e.stackTrace}")
            e.printStackTrace()
            Result.Error(Failure.ServerError)
        }
    }
}