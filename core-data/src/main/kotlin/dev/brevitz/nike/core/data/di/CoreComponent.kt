package dev.brevitz.nike.core.data.di

import dagger.Component
import io.reactivex.Scheduler
import okhttp3.OkHttpClient
import javax.inject.Singleton

@Singleton
@Component(modules = [CoreModule::class, NetworkModule::class])
interface CoreComponent : DaggerComponent {
    fun serviceCreator(): ServiceCreator
    fun componentManager(): ComponentManager
    fun okHttp(): OkHttpClient

    @ObserveThread
    fun observeThread(): Scheduler

    @SubscribeThread
    fun subscribeThread(): Scheduler
}
