package dev.brevitz.nike

import android.app.Application
import dev.brevitz.nike.core.data.di.CoreComponent
import dev.brevitz.nike.core.data.di.CoreComponentProvider
import dev.brevitz.nike.core.data.di.DaggerCoreComponent
import timber.log.Timber

class App : Application(), CoreComponentProvider {
    private val coreComponent = DaggerCoreComponent.create()

    override fun onCreate() {
        super.onCreate()
        Timber.plant(if (BuildConfig.DEBUG) Timber.DebugTree() else CrashReportingTree())
    }

    override fun coreComponent(): CoreComponent = coreComponent
}
