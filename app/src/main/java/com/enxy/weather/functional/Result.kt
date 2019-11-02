package com.enxy.weather.functional

/**
 * Represents a value of one of two possible types (a disjoint union).
 * Instances of [Result] are either an instance of [Error] or [Success].
 */
sealed class Result<out E, out S> {
    data class Error<out E>(val a: E) : Result<E, Nothing>()
    data class Success<out S>(val b: S) : Result<Nothing, S>()

    val isSuccess get() = this is Success<S>
    val isError get() = this is Error<E>

    fun <E> error(a: E) = Error(a)
    fun <S> success(b: S) = Success(b)

    fun<R> handle(fnE: (E) -> R, fnS: (S) -> R): R =
        when (this) {
            is Error -> fnE(a)
            is Success -> fnS(b)
        }
}