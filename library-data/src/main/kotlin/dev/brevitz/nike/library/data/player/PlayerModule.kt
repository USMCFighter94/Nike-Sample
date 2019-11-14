package dev.brevitz.nike.library.data.player

import dagger.Module
import dagger.Provides
import dev.brevitz.nike.core.data.FeatureScope
import dev.brevitz.nike.core.data.MemoryReactiveStore
import dev.brevitz.nike.core.data.di.ServiceCreator
import dev.brevitz.nike.library.data.player.api.PlayerApi
import dev.brevitz.nike.library.domain.player.PlayerRepository
import dev.brevitz.nike.library.domain.player.PlayerStore

@Module
object PlayerModule {
    @Provides
    @FeatureScope
    fun playerStore(): PlayerStore = MemoryReactiveStore { it.id }

    @Provides
    @FeatureScope
    fun rosterApi(serviceCreator: ServiceCreator): PlayerApi = serviceCreator.create(PlayerApi::class.java)

    @Provides
    @FeatureScope
    fun rosterRepository(store: PlayerStore, api: PlayerApi): PlayerRepository = PlayerDataRepository(store, api)
}
