package com.enxy.weather.utils.exception

/**
 * Base Class for handling errors/failures/exceptions.
 */
sealed class Failure {
    data class ConnectionError(
        val message: String?
    ) : Failure()

    data class ServerError(
        val message: String?,
        val url: String?,
        val responseCode: Int?
    ) : Failure()

    object DataNotFoundInCache : Failure()
}