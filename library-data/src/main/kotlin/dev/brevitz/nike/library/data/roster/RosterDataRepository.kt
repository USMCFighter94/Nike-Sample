package dev.brevitz.nike.library.data.roster

import dev.brevitz.nike.core.data.doIfSuccess
import dev.brevitz.nike.core.data.mapWithResult
import dev.brevitz.nike.core.data.syncIfEmpty
import dev.brevitz.nike.core.domain.ObservableRemoteData
import dev.brevitz.nike.core.domain.toMaybeError
import dev.brevitz.nike.core.domain.toRemoteData
import dev.brevitz.nike.library.data.roster.api.RosterApi
import dev.brevitz.nike.library.domain.roster.Roster
import dev.brevitz.nike.library.domain.roster.RosterRepository
import dev.brevitz.nike.library.domain.roster.RosterStore

class RosterDataRepository(private val store: RosterStore, private val api: RosterApi) : RosterRepository {

    override fun getRoster(id: Int): ObservableRemoteData<Roster> = store.get(id)
        .syncIfEmpty(fetchRoster(id))

    private fun fetchRoster(id: Int) = api.getRoster(id)
        .mapWithResult { it?.toDomain(id) }
        .map { it.toRemoteData() }
        .doIfSuccess { store.store(it) }
        .flatMapMaybe { it.toMaybeError() }
}
