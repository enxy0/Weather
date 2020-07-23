package com.enxy.weather.utils

/**
 * Represents a value of one of two possible types (a disjoint union).
 * Instances of [Result] are either an instance of [Error] or [Success].
 */
sealed class Result<out E, out S> {
    data class Error<out E>(val error: E) : Result<E, Nothing>()
    data class Success<out S>(val data: S) : Result<Nothing, S>()

    fun <R> handle(onFailure: (E) -> R, onSuccess: (S) -> R): R =
        when (this) {
            is Error -> onFailure(error)
            is Success -> onSuccess(data)
        }

    inline fun onSuccess(block: (S) -> Unit): Result<E, S> {
        if (this is Success)
            block(data)
        return this
    }

    inline fun onFailure(block: (E) -> Unit): Result<E, S> {
        if (this is Error)
            block(error)
        return this
    }
}