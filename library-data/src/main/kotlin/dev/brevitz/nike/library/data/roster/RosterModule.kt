package dev.brevitz.nike.library.data.roster

import dagger.Module
import dagger.Provides
import dev.brevitz.nike.core.data.FeatureScope
import dev.brevitz.nike.core.data.MemoryReactiveStore
import dev.brevitz.nike.core.data.di.ServiceCreator
import dev.brevitz.nike.library.data.roster.api.RosterApi
import dev.brevitz.nike.library.domain.roster.RosterRepository
import dev.brevitz.nike.library.domain.roster.RosterStore

@Module
object RosterModule {
    @Provides
    @FeatureScope
    fun rosterStore(): RosterStore = MemoryReactiveStore { it.id }

    @Provides
    @FeatureScope
    fun rosterApi(serviceCreator: ServiceCreator): RosterApi = serviceCreator.create(RosterApi::class.java)

    @Provides
    @FeatureScope
    fun rosterRepository(store: RosterStore, api: RosterApi): RosterRepository = RosterDataRepository(store, api)
}
