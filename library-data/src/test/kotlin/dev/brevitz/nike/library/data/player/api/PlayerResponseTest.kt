package dev.brevitz.nike.library.data.player.api

import com.squareup.moshi.Types
import dev.brevitz.nike.core.domain.RemoteError
import dev.brevitz.nike.core.domain.Result
import dev.brevitz.nike.library.data.loadFromFile
import dev.brevitz.nike.library.data.moshi
import dev.brevitz.nike.library.domain.player.generator.PlayerGen
import io.kotlintest.matchers.types.shouldBeTypeOf
import io.kotlintest.shouldBe
import io.kotlintest.specs.FunSpec

class PlayerResponseTest : FunSpec() {
    private val playerAdapter = moshi.adapter<PlayerResponse>(Types.newParameterizedType(PlayerResponse::class.java))

    private val playerJson = javaClass.loadFromFile("player.json")
    private val badPlayerJson = javaClass.loadFromFile("badPlayer.json")

    private val playerResponse = playerAdapter.fromJson(playerJson)
    private val badPlayerResponse = playerAdapter.fromJson(badPlayerJson)

    private val expected = PlayerGen().constants().first()

    init {
        test("Player is transformed correctly") {
            playerResponse?.toDomain() shouldBe Result.succeed(expected)
        }

        test("Parsing error returns Result.Error") {
            badPlayerResponse?.toDomain().shouldBeTypeOf<Result.Error<Nothing, RemoteError.ParsingError>>()
        }
    }
}
