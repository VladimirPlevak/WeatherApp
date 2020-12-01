package com.filit.domain.usecase

import com.filit.domain.model.WeatherLoadModel
import com.filit.domain.model.WeatherModel
import io.reactivex.rxjava3.core.Observable

interface WeatherUseCases {
    fun loadAction(action: WeatherLoadAction)
    fun loadState(): Observable<WeatherLoadState>
}

    sealed class WeatherLoadAction {
        object First: WeatherLoadAction()
        object Refresh: WeatherLoadAction()
    }

    sealed class WeatherLoadState {
        data class First(val stage: WeatherLoadStageState) : WeatherLoadState()
        data class Refresh(val stage: WeatherLoadStageState) : WeatherLoadState()
    }

    sealed class WeatherLoadStageState {
        object Start : WeatherLoadStageState()
        data class Error(val error: Throwable) : WeatherLoadStageState()
        data class Success(val weather: WeatherModel) : WeatherLoadStageState()
    }
