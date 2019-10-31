package com.enxy.weather.functional

/**
 * Represents a value of one of two possible types (a disjoint union).
 * Instances of [Result] are either an instance of [Error] or [Success].
 * FP Convention dictates that [Error] is used for "Failure"
 * and [Success] is used for "success".
 *
 * @see Error
 * @see Success
 */
sealed class Result<out E, out S> {
    /** * Represents the left side of [Result] class which by convention is a "Failure". */
    data class Error<out E>(val a: E) : Result<E, Nothing>()

    /** * Represents the right side of [Result] class which by convention is a "Success". */
    data class Success<out S>(val b: S) : Result<Nothing, S>()

    val isRight get() = this is Success<S>
    val isLeft get() = this is Error<E>

    fun <E> error(a: E) = Error(a)
    fun <S> success(b: S) = Success(b)

    fun handle(fnE: (E) -> Any, fnS: (S) -> Any): Any =
        when (this) {
            is Error -> fnE(a)
            is Success -> fnS(b)
        }
}