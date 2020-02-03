package com.enxy.weather.functional

/**
 * Represents a value of one of two possible types (a disjoint union).
 * Instances of [Result] are either an instance of [Error] or [Success].
 */
sealed class Result<out E, out S> {
    data class Error<out E>(val error: E) : Result<E, Nothing>()
    data class Success<out S>(val success: S) : Result<Nothing, S>()
}