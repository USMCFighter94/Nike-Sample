package dev.brevitz.nike.core.data.di

import dagger.Module
import dagger.Provides
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Singleton

@Module
object CoreModule {
    @Provides
    @Singleton
    fun componentManager() = ComponentManager()

    @Provides
    @ObserveThread
    fun observeThread(): Scheduler = AndroidSchedulers.mainThread()

    @Provides
    @SubscribeThread
    fun subscribeThread(): Scheduler = Schedulers.io()
}
