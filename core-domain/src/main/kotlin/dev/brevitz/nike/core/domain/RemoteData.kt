package dev.brevitz.nike.core.domain

import io.reactivex.Maybe

sealed class RemoteData<out T : Any, out U : Any> {
    object NotAsked : RemoteData<Nothing, Nothing>()

    object Loading : RemoteData<Nothing, Nothing>()

    data class Success<T : Any>(val data: T) : RemoteData<T, Nothing>()

    data class Error<out U : Any>(val error: U) : RemoteData<Nothing, U>()

    fun isNotAsked() = this is NotAsked
    fun isLoading() = this is Loading
    fun isSuccess() = this is Success
    fun isError() = this is Error

    companion object {
        fun <A : Any> succeed(data: A): RemoteData<A, Nothing> = Success(data)

        fun <E : Any> error(error: E): RemoteData<Nothing, E> = Error(error)
    }
}

fun <A : Any, E : Any> RemoteData<A, E>.toMaybeError(): Maybe<E> = when (this) {
    is RemoteData.Error -> Maybe.just(error)
    else -> Maybe.empty()
}

fun <A : Any> A.success(): RemoteData<A, Nothing> = RemoteData.Success(this)

inline fun <A : Any, B : Any, E : Any> RemoteData<A, E>.map(f: (A) -> B): RemoteData<B, E> =
    mapBoth(
        { it },
        { f(it) }
    )

inline fun <A : Any, B : Any, C : Any, E : Any> RemoteData<A, E>.mapBoth(failure: (E) -> C, success: (A) -> B): RemoteData<B, C> =
    when (this) {
        is RemoteData.NotAsked -> this
        is RemoteData.Loading -> this
        is RemoteData.Success -> RemoteData.Success(success(data))
        is RemoteData.Error -> RemoteData.Error(failure(error))
    }

inline fun <A : Any, B : Any, E : Any> RemoteData<A, E>.flatMap(f: (A) -> RemoteData<B, E>): RemoteData<B, E> =
    when (this) {
        is RemoteData.Success -> f(data)
        is RemoteData.Error -> RemoteData.Error(error)
        is RemoteData.Loading -> this
        is RemoteData.NotAsked -> this
    }
