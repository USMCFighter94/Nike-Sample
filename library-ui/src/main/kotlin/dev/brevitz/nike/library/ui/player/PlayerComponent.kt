package dev.brevitz.nike.library.ui.player

import dagger.Component
import dev.brevitz.nike.core.data.FeatureScope
import dev.brevitz.nike.core.data.di.CoreComponent
import dev.brevitz.nike.core.data.di.DaggerComponent
import dev.brevitz.nike.library.data.player.PlayerModule

@FeatureScope
@Component(dependencies = [CoreComponent::class], modules = [PlayerModule::class])
internal interface PlayerComponent : DaggerComponent {
    fun inject(dialogFragment: PlayerDetailDialogFragment)

    companion object {
        const val KEY = "PlayerComponent"
    }
}
