package com.arya199.gemstone

import androidx.lifecycle.Observer

open class Event<out T>(private val content: T) {

    var hasBeenHandled = false
        private set

    fun getContentIfNotHandled(): T? {
        return if (hasBeenHandled) {
            null
        }
        else {
            hasBeenHandled = true
            content
        }
    }
}

class EventObserver<T>(private val onEventUhandledContent: (T) -> Unit): Observer<Event<T>> {

    override fun onChanged(t: Event<T>?) {
        t?.getContentIfNotHandled()?.let {
            onEventUhandledContent(it)
        }
    }
}