package dev.brevitz.nike.library.data.roster.generator

import dev.brevitz.nike.library.domain.roster.RosterPlayer
import io.kotlintest.properties.Gen

class RosterPlayerGen : Gen<RosterPlayer> {
    override fun constants() = emptyList<RosterPlayer>()

    override fun random() = generateSequence {
        RosterPlayer(
            Gen.long().random().first(),
            Gen.string().random().first(),
            Gen.string().random().first(),
            Gen.string().random().first()
        )
    }
}
