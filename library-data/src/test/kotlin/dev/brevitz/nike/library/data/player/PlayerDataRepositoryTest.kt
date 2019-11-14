package dev.brevitz.nike.library.data.player

import com.squareup.moshi.Types
import dev.brevitz.nike.core.domain.Option
import dev.brevitz.nike.core.domain.RemoteData
import dev.brevitz.nike.library.data.loadFromFile
import dev.brevitz.nike.library.data.moshi
import dev.brevitz.nike.library.data.player.api.PlayerApi
import dev.brevitz.nike.library.data.player.api.PlayerResponse
import dev.brevitz.nike.library.domain.player.generator.PlayerGen
import dev.brevitz.nike.library.domain.player.PlayerStore
import io.kotlintest.specs.FunSpec
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import io.reactivex.Observable
import io.reactivex.Single
import retrofit2.Response

class PlayerDataRepositoryTest : FunSpec() {
    private val store = mockk<PlayerStore>()
    private val api = mockk<PlayerApi>()
    private val repository = PlayerDataRepository(store, api)

    private val playerAdapter = moshi.adapter<PlayerResponse>(Types.newParameterizedType(PlayerResponse::class.java))
    private val playerJson = javaClass.loadFromFile("player.json")
    private val playerResponse = playerAdapter.fromJson(playerJson)

    private val expected = PlayerGen().constants().first()

    init {
        test("Repository returns from store when available") {
            every { store.get(PLAYER_ID) } returns Observable.just(Option.Some(expected))
            every { api.getPlayerDetails(PLAYER_ID) } returns Observable.empty<Response<PlayerResponse>>().firstOrError()

            repository.getDetails(PLAYER_ID)
                .test()
                .assertValue(RemoteData.succeed(expected))
                .dispose()
        }

        test("Repository returns from api when store is empty") {
            every { store.get(PLAYER_ID) } returns Observable.just(Option.None)
            every { api.getPlayerDetails(PLAYER_ID) } returns Single.just(Response.success(playerResponse))

            repository.getDetails(PLAYER_ID)
                .test()
                .assertValues(RemoteData.Loading)
                .dispose()

            verify { api.getPlayerDetails(PLAYER_ID) }
        }
    }

    private companion object {
        private const val PLAYER_ID = 8477946
    }
}
