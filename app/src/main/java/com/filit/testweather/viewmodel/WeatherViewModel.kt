package com.filit.testweather.viewmodel

import androidx.lifecycle.MutableLiveData
import com.filit.domain.model.WeatherLoadModel
import com.filit.domain.model.WeatherModel
import com.filit.domain.usecase.WeatherLoadAction
import com.filit.domain.usecase.WeatherLoadStageState
import com.filit.domain.usecase.WeatherLoadState
import com.filit.domain.usecase.WeatherUseCases
import com.filit.testweather.common.extension.SingleLiveEventEmpty


class WeatherViewModel(
    private val weatherUseCases: WeatherUseCases
) : BaseViewModel() {


    var selectedPagePosition = 0

    val contentVisibilityState = MutableLiveData<ViewVisibilityState>()
    val loadProgressVisibilityState = MutableLiveData<ViewVisibilityState>()

    val errorState = MutableLiveData<ErrorState>()

    val refreshEnable = MutableLiveData<Boolean>()
    val refreshState = MutableLiveData<Boolean>()

    val weatherState = MutableLiveData<WeatherModel>()

    val openCitiesActivity = SingleLiveEventEmpty()

    init {
        initLoadState()
        firstLoadWeather()
    }

    private fun firstLoadWeather() = weatherUseCases.loadAction(
        WeatherLoadAction.First
    )

    fun refreshLoadWeather() = weatherUseCases.loadAction(
        WeatherLoadAction.Refresh
    )

    fun loadWeather() {
        weatherUseCases.loadAction(
            WeatherLoadAction.First
        )
    }

    fun clickOnCitiesButton() = openCitiesActivity.call()

    private fun initLoadState() =
        disposables.add(
            weatherUseCases.loadState()
                .subscribe {
                    when (it) {
                        is WeatherLoadState.First -> handleLoadState(it.stage)
                        is WeatherLoadState.Refresh -> handleLoadState(it.stage)
                    }
                }
        )

    private fun handleLoadState(state: WeatherLoadStageState) {
        when (state) {
            WeatherLoadStageState.Start -> {
                contentVisibilityState.value = ViewVisibilityState.Invisible
                loadProgressVisibilityState.value = ViewVisibilityState.Visible
                refreshState.value = false
                refreshEnable.value = false
            }
            is WeatherLoadStageState.Error -> {
                loadProgressVisibilityState.value = ViewVisibilityState.Invisible
                errorState.value = ErrorState(state.error)
            }
            is WeatherLoadStageState.Success -> {
                loadProgressVisibilityState.value = ViewVisibilityState.Invisible
                refreshEnable.value = true
                contentVisibilityState.value = ViewVisibilityState.Visible
                weatherState.value = state.weather
            }
        }
    }
    fun pageSelect(position: Int) {
        selectedPagePosition = position
    }


}