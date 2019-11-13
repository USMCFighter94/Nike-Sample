package dev.brevitz.nike.core.domain

import io.reactivex.Observable

interface ReactiveStore<K : Any, V : Any> {
    fun store(item: V)
    fun get(key: K): Observable<Option<V>>
    fun getAll(): Observable<Option<Set<V>>>
    fun clear()
    fun clear(key: K)
}
