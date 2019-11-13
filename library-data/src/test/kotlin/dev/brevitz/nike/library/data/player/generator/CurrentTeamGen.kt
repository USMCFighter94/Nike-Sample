package dev.brevitz.nike.library.data.player.generator

import dev.brevitz.nike.library.domain.player.CurrentTeam
import io.kotlintest.properties.Gen

class CurrentTeamGen : Gen<CurrentTeam> {
    override fun constants() = emptyList<CurrentTeam>()

    override fun random() = generateSequence {
        CurrentTeam(Gen.int().random().first(), Gen.string().random().first())
    }
}
