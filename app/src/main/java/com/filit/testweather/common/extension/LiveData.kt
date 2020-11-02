package com.filit.testweather.common.extension

import androidx.annotation.MainThread
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer

fun <T> LiveData<T>.observe(owner: LifecycleOwner, observer: (t: T) -> Unit) {
    this.observe(owner, Observer {
        it.let(observer)
    })
}

fun <T> LiveData<T>.observerForever(observer: (t: T) -> Unit) =
    Observer<T> {
        it.let(observer)
    }.also {
        this.observeForever(it)
    }

class SingleLiveEventEmpty : MutableLiveData<Unit>() {
    @MainThread
    fun call() = super.setValue(Unit)
}

open class SingleLiveEventOnce<T> : MutableLiveData<T>() {

    private var called = false

    @MainThread
    override fun setValue(t: T?) {
        if (called.not()) {
            called = true
            super.setValue(t)
        }

    }
}