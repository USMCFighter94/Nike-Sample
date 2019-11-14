package dev.brevitz.nike.library.domain.player

import dev.brevitz.nike.library.domain.player.generator.PlayerGen
import io.kotlintest.properties.forAll
import io.kotlintest.specs.FunSpec

class PlayerTest : FunSpec() {
    private val playerGen = PlayerGen()

    init {
        test("Image Url") {
            forAll(playerGen) {
                it.image == "https://nhl.bamcontent.com/images/headshots/current/168x168/${it.id}@3x.jpg"
            }
        }
    }
}
