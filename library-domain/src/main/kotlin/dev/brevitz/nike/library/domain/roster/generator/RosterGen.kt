package dev.brevitz.nike.library.domain.roster.generator

import dev.brevitz.nike.library.domain.roster.Roster
import dev.brevitz.nike.library.domain.roster.RosterPlayer
import io.kotlintest.properties.Gen

class RosterGen : Gen<Roster> {

    override fun constants() = listOf(
        Roster(
            17,
            listOf(
                RosterPlayer(8477479, "Tyler Bertuzzi", "59", "Left Wing"),
                RosterPlayer(8477511, "Anthony Mantha", "39", "Right Wing"),
                RosterPlayer(8477946, "Dylan Larkin", "71", "Center")
            )
        )
    )

    override fun random(): Sequence<Roster> {
        val rosterPlayerGen = RosterPlayerGen()

        return generateSequence {
            Roster(
                Gen.int().random().first(),
                rosterPlayerGen.random().take(10).toList()
            )
        }
    }
}
