package dev.brevitz.nike.core.domain

sealed class RemoteError {
    object SyncError : RemoteError()
    data class HttpError(val code: Int, val message: String) : RemoteError()
    data class ParsingError(val error: Throwable) : RemoteError()
}
