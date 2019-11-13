package dev.brevitz.nike.library.data.player.api

import dev.brevitz.nike.core.domain.Result
import dev.brevitz.nike.core.domain.attemptTransform
import dev.brevitz.nike.library.data.api.PositionResponse
import dev.brevitz.nike.library.domain.player.CurrentTeam
import dev.brevitz.nike.library.domain.player.Player

data class PersonResponse(
    val id: Long?,
    val fullName: String?,
    val link: String?,
    val firstName: String?,
    val lastName: String?,
    val primaryNumber: String?,
    val birthDate: String?,
    val currentAge: Int?,
    val birthCity: String?,
    val birthStateProvince: String?,
    val birthCountry: String?,
    val nationality: String?,
    val height: String?,
    val weight: Int?,
    val active: Boolean?,
    val alternateCaptain: Boolean?,
    val captain: Boolean?,
    val rookie: Boolean?,
    val shootsCatches: String?,
    val rosterStatus: String?,
    val currentTeam: CurrentTeamResponse?,
    val primaryPosition: PositionResponse?
) {
    fun toDomain() = attemptTransform {
        require(id != null) { "Must provide player id" }

        require(!fullName.isNullOrBlank()) { "Must provide player full name" }

        require(!firstName.isNullOrBlank()) { "Must provide player first name" }

        require(!lastName.isNullOrBlank()) { "Must provide player last name" }

        require(!primaryNumber.isNullOrBlank()) { "Must provide player number" }

        require(!fullName.isNullOrBlank()) { "Must provide player full name" }

        val team = currentTeam?.toDomain()
        require(team is Result.Success) {
            when (team) {
                is Result.Error -> team.error.toString()
                else -> "Must provide player's team"
            }
        }

        val position = primaryPosition?.toDomain()
        require(position is Result.Success) {
            when (position) {
                is Result.Error -> position.error.toString()
                else -> "Must provide player's position"
            }
        }

        Player(
            id,
            fullName,
            firstName,
            lastName,
            primaryNumber,
            alternateCaptain ?: false,
            captain ?: false,
            rookie ?: false,
            shootsCatches,
            team.data,
            position.data
        )
    }

    data class CurrentTeamResponse(val id: Int, val name: String?, val link: String?) {
        fun toDomain() = attemptTransform {
            require(!name.isNullOrBlank()) { "Must provide team name" }

            CurrentTeam(id, name)
        }
    }
}
