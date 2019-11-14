package dev.brevitz.nike.library.domain.player

import dev.brevitz.nike.core.domain.ObservableRemoteData

interface PlayerRepository {
    fun getDetails(id: Long): ObservableRemoteData<Player>
}
