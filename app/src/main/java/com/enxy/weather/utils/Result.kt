package com.enxy.weather.utils

/**
 * Represents a value of one of two possible types (a disjoint union).
 * Instances of [Result] are either an instance of [Error] or [Success].
 */
sealed class Result<out E, out S> {
    data class Error<out E>(val error: E) : Result<E, Nothing>()
    data class Success<out S>(val data: S) : Result<Nothing, S>()

    fun <R> handle(fnE: (E) -> R, fnS: (S) -> R): R =
        when (this) {
            is Error -> fnE(error)
            is Success -> fnS(data)
        }

    inline fun onSuccess(block: Success<S>.() -> Unit): Result<E, S> {
        if (this is Success)
            block()
        return this
    }

    inline fun onFailure(block: Error<E>.() -> Unit): Result<E, S> {
        if (this is Error)
            block()
        return this
    }
}