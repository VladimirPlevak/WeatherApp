package com.filit.testweather.viewmodel

import androidx.lifecycle.ViewModel
import io.reactivex.rxjava3.disposables.CompositeDisposable

open class BaseViewModel : ViewModel() {

    protected val disposables = CompositeDisposable()

    override fun onCleared() {
        super.onCleared()
        disposables.dispose()
    }
}

sealed class ViewVisibilityState {
    object Visible : ViewVisibilityState()
    object Invisible : ViewVisibilityState()
    object Gone : ViewVisibilityState()
}

data class ErrorState(val error: Throwable)
