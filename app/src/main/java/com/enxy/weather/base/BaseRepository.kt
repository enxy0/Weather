package com.enxy.weather.base

import com.enxy.weather.exception.Failure
import com.enxy.weather.functional.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException


open class BaseRepository {
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
                    Result.Error(Failure.ServerError)
            } else {
                Result.Error(Failure.ServerResponseError)
            }
        } catch (e: IOException) {
            e.printStackTrace()
            Result.Error(Failure.ConnectionError)
        } catch (e: HttpException) {
            e.printStackTrace()
            Result.Error(Failure.ServerResponseError)
        } catch (throwable: Throwable) {
            throwable.printStackTrace()
            Result.Error(Failure.ServerError)
        }
    }
}