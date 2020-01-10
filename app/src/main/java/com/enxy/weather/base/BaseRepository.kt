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
                    val model = withContext(Dispatchers.IO) { transform(body) }
                    Result.Success(model)
                } else
                    Result.Error(Failure.ServerError)
            } else {
                Log.d(
                    "BaseRepository",
                    "safeApiCall: response.isSuccessful=${response.isSuccessful}"
                )
                Log.d("BaseRepository", "safeApiCall: response.message()=${response.message()}")
                Log.d("BaseRepository", "safeApiCall: response.code()=${response.code()}")
                Log.d("BaseRepository", "safeApiCall: response.body()=${response.body()}")
                Result.Error(Failure.ServerResponseError)
            }
        } catch (e: Throwable) {
            Log.e("BaseRepository", "safeApiCall: Exception: ${e.message} & ${e.stackTrace}")
            e.printStackTrace()
            Result.Error(Failure.ServerError)
        }
    }
}