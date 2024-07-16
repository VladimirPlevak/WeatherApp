package com.filit.domain.interactor

import com.filit.domain.repository.AppRepository
import com.filit.domain.repository.CitiesRepository
import com.filit.domain.repository.SchedulerRepository
import com.filit.domain.usecase.*
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.subjects.ReplaySubject

class CitiesInteractor(
    private val citiesRepository: CitiesRepository,
    private val appRepository: AppRepository,
    private val schedulerRepository: SchedulerRepository
):CitiesUseCases {

    private val loadSubject by lazy { ReplaySubject.create<CitiesAction>()}

    override fun loadCitiesAction(action: CitiesAction) = loadSubject.onNext(action)
    override fun changeCurrentCityAction(city: String) = appRepository.changeCurrentCity(city = city)
    override fun loadState(): Observable<CitiesLoadState> =
        loadSubject
            .switchMap { loadAction ->
                when (loadAction) {
                    is CitiesAction.LoadCitiesAction ->
                        loadCitiesStageState(appRepository.getCities())
                        .map { CitiesLoadState.LoadCitiesState(it) }
                        .subscribeOn(schedulerRepository.io())
                    is CitiesAction.AddCityAction ->
                        addCityStageState(loadAction.city)
                            .map { CitiesLoadState.AddCityState(it) }

                }.observeOn(schedulerRepository.ui())
            }

    fun newfunc (){}

    private fun loadCitiesStageState (cityLoadModelsList: List<String>) =
        citiesRepository
            .loadCitiesRemote(cityLoadModelsList)
            .subscribeOn(schedulerRepository.io())
            .map { CitiesLoadStageState.Success(it) as CitiesLoadStageState }
            .toObservable()
            .observeOn(schedulerRepository.ui())
            .startWithItem(CitiesLoadStageState.Start)
            .onErrorReturn { CitiesLoadStageState.Error(it) }

    private fun addCityStageState (cityModel: String) =
        citiesRepository
            .loadCityRemote(cityModel)
            .subscribeOn(schedulerRepository.io())
            .map {
                appRepository.saveCity(it.city)
                return@map it
            }
            .map { CityAddStageState.Success(it) as CityAddStageState }
            .observeOn(schedulerRepository.ui())
            .toObservable()
            .startWithItem(CityAddStageState.Start)
            .onErrorReturn {error->
                CityAddStageState.Error("Ошибка при добавлении города") }
}