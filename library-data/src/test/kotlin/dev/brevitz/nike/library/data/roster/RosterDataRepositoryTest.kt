package dev.brevitz.nike.library.data.roster

import com.squareup.moshi.Types
import dev.brevitz.nike.core.domain.Option
import dev.brevitz.nike.core.domain.RemoteData
import dev.brevitz.nike.library.data.loadFromFile
import dev.brevitz.nike.library.data.moshi
import dev.brevitz.nike.library.data.roster.api.RosterApi
import dev.brevitz.nike.library.data.roster.api.RosterResponse
import dev.brevitz.nike.library.data.roster.generator.RosterGen
import dev.brevitz.nike.library.domain.roster.RosterStore
import io.kotlintest.specs.FunSpec
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import io.reactivex.Observable
import io.reactivex.Single
import retrofit2.Response

class RosterDataRepositoryTest : FunSpec() {
    private val store = mockk<RosterStore>()
    private val api = mockk<RosterApi>()
    private val repository = RosterDataRepository(store, api)

    private val rosterAdapter = moshi.adapter<RosterResponse>(Types.newParameterizedType(RosterResponse::class.java))
    private val rosterJson = javaClass.loadFromFile("roster.json")
    private val rosterResponse = rosterAdapter.fromJson(rosterJson)

    private val expected = RosterGen().constants().first()

    init {
        test("Repository returns from store when available") {
            every { store.get(ROSTER_ID) } returns Observable.just(Option.Some(expected))
            every { api.getRoster(ROSTER_ID) } returns Observable.empty<Response<RosterResponse>>().firstOrError()

            repository.getRoster(ROSTER_ID)
                .test()
                .assertValue(RemoteData.succeed(expected))
                .dispose()
        }

        test("Repository returns from api when store is empty") {
            every { store.get(ROSTER_ID) } returns Observable.just(Option.None)
            every { api.getRoster(ROSTER_ID) } returns Single.just(Response.success(rosterResponse))

            repository.getRoster(ROSTER_ID)
                .test()
                .assertValues(RemoteData.Loading)
                .dispose()

            verify { api.getRoster(ROSTER_ID) }
        }
    }

    private companion object {
        private const val ROSTER_ID = 17
    }
}
