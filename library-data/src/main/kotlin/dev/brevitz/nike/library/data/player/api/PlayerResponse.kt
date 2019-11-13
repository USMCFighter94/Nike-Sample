package dev.brevitz.nike.library.data.player.api

import dev.brevitz.nike.core.domain.RemoteError
import dev.brevitz.nike.core.domain.Result
import dev.brevitz.nike.core.domain.attemptTransform
import dev.brevitz.nike.library.domain.player.Player

data class PlayerResponse(val copyright: String?, val people: List<PersonResponse>?) {
    fun toDomain(): Result<Player, RemoteError> = attemptTransform {
        val player = people?.firstOrNull()?.toDomain()

        require(player is Result.Success) {
            when (player) {
                is Result.Error -> player.error.toString()
                else -> "Must provide player"
            }
        }

        player.data
    }
}
