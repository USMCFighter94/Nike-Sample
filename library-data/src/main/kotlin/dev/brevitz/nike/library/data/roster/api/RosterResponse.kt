package dev.brevitz.nike.library.data.roster.api

import dev.brevitz.nike.core.domain.attemptTransform
import dev.brevitz.nike.core.domain.successOrNull
import dev.brevitz.nike.library.domain.roster.Roster

data class RosterResponse(val copyright: String?, val roster: List<RosterPlayerResponse>?) {
    fun toDomain(id: Int) = attemptTransform {
        require(roster != null) { "Must provider a roster" }
        Roster(id, roster.mapNotNull { it.toDomain().successOrNull() })
    }
}
