package dev.brevitz.nike.library.domain.roster.generator

import dev.brevitz.nike.library.domain.roster.RosterPlayer
import io.kotlintest.properties.Gen

class RosterPlayerGen : Gen<RosterPlayer> {
    override fun constants() = listOf(
        RosterPlayer(8477479, "Tyler Bertuzzi", "59", "Left Wing"),
        RosterPlayer(8477511, "Anthony Mantha", "39", "Right Wing"),
        RosterPlayer(8477946, "Dylan Larkin", "71", "Center")
    )

    override fun random() = generateSequence {
        RosterPlayer(
            Gen.long().random().first(),
            Gen.string().random().first(),
            Gen.string().random().first(),
            Gen.string().random().first()
        )
    }
}
