package com.enxy.weather.utils.exception

/**
 * Base Class for handling errors/failures/exceptions.
 */
sealed class Failure {
    object NoConnection: Failure()

    object BadServerResponse: Failure()

    object DataNotFound : Failure()

    object LocationsNotFound : Failure()
}
