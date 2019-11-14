package dev.brevitz.nike.library.ui.roster

import dagger.Component
import dev.brevitz.nike.core.data.FeatureScope
import dev.brevitz.nike.core.data.di.CoreComponent
import dev.brevitz.nike.core.data.di.DaggerComponent
import dev.brevitz.nike.library.data.roster.RosterModule

@FeatureScope
@Component(dependencies = [CoreComponent::class], modules = [RosterModule::class])
internal interface RosterComponent : DaggerComponent {
    fun inject(view: RosterView)

    companion object {
        const val KEY = "RosterComponent"
    }
}
