package dev.brevitz.nike.library.data.player.generator

import dev.brevitz.nike.library.domain.player.CurrentTeam
import dev.brevitz.nike.library.domain.player.Player
import io.kotlintest.properties.Gen

class PlayerGen : Gen<Player> {
    override fun constants() = listOf(
        Player(
            8477946,
            "Dylan Larkin",
            "Dylan",
            "Larkin",
            "71",
            alternateCaptain = true,
            captain = false,
            rookie = false,
            shootsCatches = "L",
            currentTeam = CurrentTeam(17, "Detroit Red Wings"),
            position = "Center"
        )
    )

    override fun random(): Sequence<Player> {
        val teamGen = CurrentTeamGen()

        return generateSequence {
            Player(
                Gen.long().random().first(),
                Gen.string().random().first(),
                Gen.string().random().first(),
                Gen.string().random().first(),
                Gen.string().random().first(),
                Gen.bool().random().first(),
                Gen.bool().random().first(),
                Gen.bool().random().first(),
                Gen.string().random().first(),
                teamGen.random().first(),
                Gen.string().random().first()
            )
        }
    }
}
