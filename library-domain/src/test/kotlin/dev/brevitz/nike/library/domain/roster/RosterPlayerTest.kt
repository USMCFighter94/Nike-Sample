package dev.brevitz.nike.library.domain.roster

import dev.brevitz.nike.library.domain.roster.generator.RosterPlayerGen
import io.kotlintest.properties.forAll
import io.kotlintest.specs.FunSpec

class RosterPlayerTest : FunSpec() {
    private val playerGen = RosterPlayerGen()

    init {
        test("Image Url") {
            forAll(playerGen) {
                it.image == "https://nhl.bamcontent.com/images/headshots/current/168x168/${it.id}@3x.jpg"
            }
        }
    }
}
