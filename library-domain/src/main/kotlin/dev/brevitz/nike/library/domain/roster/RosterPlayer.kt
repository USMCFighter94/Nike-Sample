package dev.brevitz.nike.library.domain.roster

data class RosterPlayer(val id: Long, val fullName: String, val jerseyNumber: String, val position: String) {
    val image = String.format(IMAGE_URL, id)

    private companion object {
        private const val IMAGE_URL = "https://nhl.bamcontent.com/images/headshots/current/168x168/%s@3x.jpg"
    }
}
