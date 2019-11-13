package dev.brevitz.nike.core.data

import dev.brevitz.nike.core.domain.RemoteData
import dev.brevitz.nike.core.domain.RemoteError
import dev.brevitz.nike.core.domain.Result
import io.reactivex.Single
import retrofit2.Response

inline fun <A : Any, B : Any> Single<Response<A>>.mapWithResult(crossinline converter: (A?) -> Result<B, RemoteError>?): Single<Result<B, RemoteError>> =
    map {
        if (it.isSuccessful) {
            converter(it.body()) ?: Result.fail(RemoteError.SyncError)
        } else {
            Result.fail(RemoteError.HttpError(it.code(), it.message()))
        }
    }

inline fun <A : Any> Single<RemoteData<A, RemoteError>>.doIfSuccess(crossinline onSuccess: (A) -> Unit): Single<RemoteData<A, RemoteError>> =
    doOnSuccess {
        if (it.isSuccess()) {
            onSuccess((it as RemoteData.Success).data)
        }
    }
