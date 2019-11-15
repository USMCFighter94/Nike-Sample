package dev.brevitz.nike.library.ui

import androidx.test.espresso.IdlingResource
import okhttp3.OkHttpClient

class OkHttpIdlingResource(private val client: OkHttpClient) : IdlingResource {

    @Volatile
    private var callback: IdlingResource.ResourceCallback? = null

    init {
        client.dispatcher.idleCallback = Runnable {
            callback?.apply {
                onTransitionToIdle()
            }
        }
    }

    override fun getName(): String = "Client"

    override fun isIdleNow(): Boolean = (client.dispatcher.runningCallsCount() == 0)
        .also {
            callback?.apply {
                if (it) {
                    onTransitionToIdle()
                }
            }
        }

    override fun registerIdleTransitionCallback(callback: IdlingResource.ResourceCallback) {
        this.callback = callback
    }
}
