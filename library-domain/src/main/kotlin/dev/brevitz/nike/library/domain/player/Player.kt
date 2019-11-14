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
) {
    val image = String.format(IMAGE_URL, id)

    private companion object {
        private const val IMAGE_URL = "https://nhl.bamcontent.com/images/headshots/current/168x168/%s@3x.jpg"
    }
}
