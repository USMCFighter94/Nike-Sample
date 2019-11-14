package dev.brevitz.nike.library.data.player.api

import io.reactivex.Single
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface PlayerApi {
    @GET("people/{id}")
    fun getPlayerDetails(@Path("id") id: Long): Single<Response<PlayerResponse>>
}
