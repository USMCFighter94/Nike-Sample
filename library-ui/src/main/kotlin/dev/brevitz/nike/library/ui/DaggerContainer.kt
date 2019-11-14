package dev.brevitz.nike.library.ui

import dev.brevitz.nike.core.data.di.CoreComponent
import dev.brevitz.nike.core.data.di.DaggerCoreComponent

object DaggerContainer {
    val coreComponent: CoreComponent = DaggerCoreComponent.create()
}
