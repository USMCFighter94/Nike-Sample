package dev.brevitz.nike.library.data.roster.api

import com.squareup.moshi.Types
import dev.brevitz.nike.core.domain.Result
import dev.brevitz.nike.library.data.loadFromFile
import dev.brevitz.nike.library.data.moshi
import dev.brevitz.nike.library.data.roster.generator.RosterGen
import io.kotlintest.shouldBe
import io.kotlintest.specs.FunSpec

class RosterResponseTest : FunSpec() {
    private val rosterAdapter = moshi.adapter<RosterResponse>(Types.newParameterizedType(RosterResponse::class.java))

    private val rosterJson = javaClass.loadFromFile("roster.json")

    private val rosterResponse = rosterAdapter.fromJson(rosterJson)

    private val expected = RosterGen().constants().first()

    init {
        test("roster is transformed correctly") {
            rosterResponse?.toDomain(17) shouldBe Result.succeed(expected)
        }
    }
}
