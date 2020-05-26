package eu.mrupp.citytracker.util

import java.lang.ref.WeakReference

object LogManager {
    private var observerRef: WeakReference<Runnable?> = WeakReference(null)

    val logMessages = mutableListOf<String>()

    fun log(message: String) {
        logMessages.add(message)
        observerRef.get()?.run()
    }

    fun observe(observer: Runnable) {
        observerRef = WeakReference(observer)
    }

    fun getAsString()= logMessages.joinToString("\n")
}