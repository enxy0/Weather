package com.enxy.weather.exception

/**
 * Base Class for handling errors/failures/exceptions.
 */
sealed class Failure {
    object ConnectionError : Failure()
    object ServerError : Failure()
    object ServerResponseError : Failure()
}