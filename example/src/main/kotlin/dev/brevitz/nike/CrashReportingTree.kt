package dev.brevitz.nike

import android.util.Log
import timber.log.Timber

class CrashReportingTree : Timber.Tree() {
    override fun isLoggable(tag: String?, priority: Int) = priority >= Log.INFO

    override fun log(priority: Int, tag: String?, message: String, t: Throwable?) {
        if (priority == Log.VERBOSE || priority == Log.DEBUG) {
            return
        }

        // Add crash libraries here

        t?.let {
            when (priority) {
                Log.ERROR -> {
                    // Log error with crash library
                }
                Log.WARN -> {
                    // Log warning with crash library
                }
                else -> {
                }
            }
        }
    }
}
