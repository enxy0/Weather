package com.enxy.weather.base

import android.util.Log
import com.enxy.weather.utils.Result
import com.enxy.weather.utils.exception.Failure
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response
import java.io.IOException


interface NetworkRepository {
    suspend fun <JSON, MODEL> safeApiCall(
        call: suspend () -> Response<JSON>,
        transform: (JSON) -> MODEL
    ): Result<Failure, MODEL> = withContext(Dispatchers.Main) {
        try {
            val response = withContext(Dispatchers.IO) { call.invoke() }
            if (response.isSuccessful) {
                val body = response.body()
                if (body != null) {
                    val model = withContext(Dispatchers.Default) { transform(body) }
                    Result.Success(model)
                } else
                    Result.Error(
                        Failure.ServerError(
                            "Response.body() is null",
                            "${response.raw().request().url()}",
                            response.code()
                        )
                    )
            } else {
                Result.Error(
                    Failure.ServerError(
                        "Response was not successful",
                        "${response.raw().request().url()}",
                        response.code()
                    )
                )
            }
        } catch (e: IOException) {
            e.printStackTrace()
            Log.d("BaseRepository", "safeApiCall: e.message=${e.message}")
            Result.Error(Failure.ConnectionError(e.message))
        } catch (throwable: Throwable) {
            throwable.printStackTrace()
            Result.Error(Failure.ServerError(throwable.message, null, null))
        }
    }
}