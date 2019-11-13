package dev.brevitz.nike.library.data.roster.api

import io.reactivex.Single
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface RosterApi {
    @GET("teams/{id}/roster")
    fun getRoster(@Path("id") id: Int): Single<Response<RosterResponse>>
}
