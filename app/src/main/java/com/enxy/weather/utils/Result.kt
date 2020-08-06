package com.enxy.weather.utils


sealed class Result<out S> {
    data class Success<out S>(val data: S) : Result<S>()
    data class Error(val exception: Exception) : Result<Nothing>()

    inline fun <R> fold(onFailure: (Exception) -> R, onSuccess: (S) -> R): R =
        when (this) {
            is Error -> onFailure(exception)
            is Success -> onSuccess(data)
        }

    inline fun onSuccess(block: (data: S) -> Unit): Result<S> {
        if (this is Success)
            block(data)
        return this
    }

    inline fun onFailure(block: (exception: Exception) -> Unit): Result<S> {
        if (this is Error)
            block(exception)
        return this
    }
}