package dev.brevitz.nike.core.domain

sealed class Option<out A : Any> {
    object None : Option<Nothing>()

    data class Some<out A : Any>(val value: A) : Option<A>()

    fun isSome(): Boolean = this is Some

    fun isNone(): Boolean = this is None

    inline fun <B : Any> map(mapper: (A) -> B): Option<B> = flatMap { Some(mapper(it)) }

    inline fun <B : Any> flatMap(mapper: (A) -> Option<B>): Option<B> = when (this) {
        is None -> this
        is Some -> mapper(value)
    }

    inline fun filter(predicate: (A) -> Boolean): Option<A> = flatMap { if (predicate(it)) Some(it) else None }
}

fun <A : Any> A?.toOption(): Option<A> = if (this == null) Option.None else Option.Some(this)

fun <A : Any> Option<A>.orNull(): A? = when (this) {
    is Option.None -> null
    is Option.Some -> value
}

fun <A : Any, E : Any> Option<A>.toRemoteData(emptyValue: RemoteData<A, E> = RemoteData.Loading): RemoteData<A, E> =
    when (this) {
        is Option.None -> emptyValue
        is Option.Some -> RemoteData.succeed(value)
    }
