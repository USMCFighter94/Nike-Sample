package dev.brevitz.nike.core.data

import dev.brevitz.nike.core.domain.ObservableRemoteData
import dev.brevitz.nike.core.domain.Option
import dev.brevitz.nike.core.domain.RemoteData
import dev.brevitz.nike.core.domain.RemoteError
import dev.brevitz.nike.core.domain.toRemoteData
import io.reactivex.Maybe
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.MaybeSubject

fun <A : Any> Observable<Option<A>>.syncIfEmpty(fetch: Maybe<RemoteError>): ObservableRemoteData<A> =
    syncIfEmpty(fetch) { RemoteError.SyncError }

inline fun <A : Any, E : Any> Observable<Option<A>>.syncIfEmpty(
    fetch: Maybe<E>,
    crossinline errorConverter: (Throwable) -> E
): Observable<RemoteData<A, E>> {
    val errorEmitter = MaybeSubject.create<RemoteData<A, E>>()

    return map { it.toRemoteData<A, E>(emptyValue = RemoteData.Loading) }
        .mergeWith(errorEmitter)
        .doOnNext {
            if (it.isLoading()) {
                fetch.subscribeOn(Schedulers.io()).subscribe(
                    { apiError -> errorEmitter.onSuccess(RemoteData.error(apiError)) },
                    { ioError -> errorEmitter.onSuccess(RemoteData.error(errorConverter(ioError))) },
                    { errorEmitter.onComplete() }
                )
            }
        }
}
