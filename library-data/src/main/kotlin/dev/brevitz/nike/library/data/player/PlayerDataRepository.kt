package dev.brevitz.nike.library.data.player

import dev.brevitz.nike.core.data.doIfSuccess
import dev.brevitz.nike.core.data.mapWithResult
import dev.brevitz.nike.core.data.syncIfEmpty
import dev.brevitz.nike.core.domain.ObservableRemoteData
import dev.brevitz.nike.core.domain.toMaybeError
import dev.brevitz.nike.core.domain.toRemoteData
import dev.brevitz.nike.library.data.player.api.PlayerApi
import dev.brevitz.nike.library.domain.player.Player
import dev.brevitz.nike.library.domain.player.PlayerRepository
import dev.brevitz.nike.library.domain.player.PlayerStore

class PlayerDataRepository(private val store: PlayerStore, private val api: PlayerApi) : PlayerRepository {

    override fun getDetails(id: Int): ObservableRemoteData<Player> = store.get(id)
        .syncIfEmpty(fetchPlayerDetails(id))

    private fun fetchPlayerDetails(id: Int) = api.getPlayerDetails(id)
        .mapWithResult { it?.toDomain() }
        .map { it.toRemoteData() }
        .doIfSuccess { store.store(it) }
        .flatMapMaybe { it.toMaybeError() }
}
