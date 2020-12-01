package com.filit.domain.interactor

import com.filit.domain.repository.AppRepository
import com.filit.domain.repository.SchedulerRepository
import com.filit.domain.repository.WeatherRepository
import com.filit.domain.usecase.WeatherLoadAction
import com.filit.domain.usecase.WeatherLoadStageState
import com.filit.domain.usecase.WeatherLoadState
import com.filit.domain.usecase.WeatherUseCases
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.subjects.ReplaySubject

class WeatherInteractor(
    private val weatherRepository: WeatherRepository,
    private val schedulerRepository: SchedulerRepository,
    private val appRepository: AppRepository
): WeatherUseCases {

    private val loadSubject by lazy {ReplaySubject.create<WeatherLoadAction>()}
    override fun loadAction(action: WeatherLoadAction) =
        loadSubject.onNext(action)

    override fun loadState(): Observable<WeatherLoadState> =
        loadSubject
            .switchMap {action->
                when(action){
                    is WeatherLoadAction.First ->
                        loadWeatherStageState(appRepository.getCurrentCity())
                            .map { WeatherLoadState.First(it) }
                    is WeatherLoadAction.Refresh->
                        loadWeatherStageState(appRepository.getCurrentCity())
                            .map { WeatherLoadState.Refresh(it) }
                }.subscribeOn(schedulerRepository.io())
            }.observeOn(schedulerRepository.ui())


    private fun loadWeatherStageState (city: String) =
        weatherRepository
            .loadWeatherForecast(city)
            .map { WeatherLoadStageState.Success(it) as WeatherLoadStageState }
            .toObservable()
            .startWithItem(WeatherLoadStageState.Start)
            .onErrorReturn { WeatherLoadStageState.Error(it) }

}