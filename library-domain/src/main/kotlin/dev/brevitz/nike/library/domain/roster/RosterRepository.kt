package dev.brevitz.nike.library.domain.roster

import dev.brevitz.nike.core.domain.ObservableRemoteData

interface RosterRepository {
    fun getRoster(id: Int): ObservableRemoteData<Roster>
}
