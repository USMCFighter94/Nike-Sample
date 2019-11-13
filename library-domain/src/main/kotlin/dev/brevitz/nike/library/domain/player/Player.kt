package dev.brevitz.nike.library.domain.player

data class Player(
    val id: Long,
    val fullName: String,
    val firstName: String,
    val lastName: String,
    val jerseyNumber: String,
    val alternateCaptain: Boolean,
    val captain: Boolean,
    val rookie: Boolean,
    val shootsCatches: String?,
    val currentTeam: CurrentTeam,
    val position: String
)
