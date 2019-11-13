package dev.brevitz.nike.library.data.roster.api

import dev.brevitz.nike.core.domain.attemptTransform
import dev.brevitz.nike.library.data.api.PositionResponse
import dev.brevitz.nike.library.domain.roster.RosterPlayer

data class RosterPlayerResponse(
    val person: PersonResponse?,
    val jerseyNumber: String?,
    val position: PositionResponse?,
    val link: String?
) {

    fun toDomain() = attemptTransform {
        val id = person?.id
        require(id != null) { "Must provide player id" }

        val name = person?.fullName
        require(!name.isNullOrBlank()) { "Must provider player name" }

        require(!jerseyNumber.isNullOrBlank()) { "Must provider jersey number" }

        val pos = position?.name
        require(!pos.isNullOrBlank()) { "Must provide position" }

        RosterPlayer(
            id,
            name,
            jerseyNumber,
            pos
        )
    }

    data class PersonResponse(val id: Long, val fullName: String?, val link: String?)
}
