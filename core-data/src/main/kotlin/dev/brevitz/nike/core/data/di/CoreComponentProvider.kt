package dev.brevitz.nike.core.data.di

import android.content.Context

interface CoreComponentProvider {
    fun coreComponent(): CoreComponent
}

fun Context.provideCoreComponent(): CoreComponent {
    val appContext = applicationContext
    return if (appContext is CoreComponentProvider) {
        appContext.coreComponent()
    } else {
        throw IllegalStateException("context must implement CoreComponentProvider")
    }
}
