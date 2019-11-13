package dev.brevitz.nike.core.domain

import io.reactivex.Maybe

sealed class Result<out T : Any, out E : Any> {

    data class Success<out T : Any, out E : Any>(val data: T) : Result<T, E>()
    data class Error<out T : Any, out E : Any>(val error: E) : Result<T, E>()

    inline fun <A : Any> map(mapFunction: (T) -> A): Result<A, E> {
        return when (this) {
            is Success -> Success(mapFunction(data))
            is Error -> Error(error)
        }
    }

    inline fun <F : Any> mapError(transform: (E) -> F): Result<T, F> {
        return when (this) {
            is Success -> Success(data)
            is Error -> Error(transform(error))
        }
    }

    inline fun doOnSuccess(action: (T) -> Unit) {
        when (this) {
            is Success -> action(data)
        }
    }

    companion object {
        fun <A : Any> succeed(data: A): Result<A, Nothing> = Result.Success(data)

        fun <E : Any> fail(error: E): Result<Nothing, E> = Result.Error(error)
    }
}

inline fun <A : Any, E : Any, B : Any> Result<A, E>.flatMap(mapFunction: (A) -> Result<B, E>): Result<B, E> {
    return when (this) {
        is Result.Success -> mapFunction(data)
        is Result.Error -> Result.Error(error)
    }
}

fun <A : Any, E : Any> Result<A, E>.toMaybeError(): Maybe<E> = when (this) {
    is Result.Success -> Maybe.empty()
    is Result.Error -> Maybe.just(error)
}

fun <A : Any, E : Any> Collection<Result<A, E>>.filterOutFailures() = filter { it is Result.Success }

fun <A : Any, E : Any> Collection<Result<A, E>>.getSuccessData() =
    filterOutFailures().map { (it as Result.Success).data }

fun <E : Any, A : Any, B : Any> Collection<A>.mapNotFailure(f: (A) -> Result<E, B>) =
    mapNotNull { f(it).successOrNull() }

fun <K : Any, E : Any, A : Any> Map<K, Result<A, E>>.filterOutFailures() = filterValues { it is Result.Success }

fun <K : Any, E : Any, A : Any> Map<K, Result<A, E>>.getSuccessData() =
    filterOutFailures().mapValues { (it.value as Result.Success).data }

fun <A : Any, E : Any> Result<A, E>?.successOrNull() = when (this) {
    is Result.Success -> this.data
    else -> null
}

fun <A : Any, E : Any> Result<A, E>.getOrDefault(default: A) = when (this) {
    is Result.Success -> this.data
    is Result.Error -> default
}

inline fun <A : Any> attempt(f: () -> A): Result<A, Throwable> = try {
    Result.Success(f())
} catch (e: Exception) {
    Result.Error(e)
}

inline fun <A : Any> attemptTransform(f: () -> A): Result<A, RemoteError> = attempt { f() }
    .mapError {
        println("ERROR:!! $it")
        RemoteError.ParsingError(it)
    }

fun <A : Any, E : Any> Result<A, E>.toRemoteData(): RemoteData<A, E> = when (this) {
    is Result.Success -> RemoteData.succeed(data)
    is Result.Error -> RemoteData.error(error)
}

fun <T : Any, E : Any> Result<T, E>.toOption(): Option<T> =
    if (this is Result.Success) Option.Some(this.data) else Option.None
