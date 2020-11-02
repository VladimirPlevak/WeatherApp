package com.filit.domain.interactor

import com.filit.domain.model.CityLoadModel
import com.filit.domain.repository.CitiesRepository
import com.filit.domain.repository.SchedulerRepository
import com.filit.domain.usecase.*
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.subjects.ReplaySubject

class CitiesInteractor(
    private val citiesRepository: CitiesRepository,
    private val schedulerRepository: SchedulerRepository
):CitiesUseCases {

    private val loadSubject by lazy { ReplaySubject.create<CitiesAction>()}

    override fun loadCitiesAction(action: CitiesAction) = loadSubject.onNext(action)

    override fun loadState(): Observable<CitiesLoadState> =
        loadSubject
            .switchMap { loadAction ->
                when (loadAction) {
                    is CitiesAction.LoadCitiesAction ->
                        loadCitiesStageState(loadAction.cityList)
                        .map { CitiesLoadState.LoadCitiesState(it) }
                        .subscribeOn(schedulerRepository.io())
                    is CitiesAction.AddCityAction ->
                        addCityStageState(loadAction.city)
                            .map { CitiesLoadState.AddCityState(it) }

                }.observeOn(schedulerRepository.ui())
            }

    private fun loadCitiesStageState (cityLoadModelsList: List<CityLoadModel>) =
        citiesRepository
            .loadCities(cityLoadModelsList)
            .subscribeOn(schedulerRepository.io())
            .map { CitiesLoadStageState.Success(it) as CitiesLoadStageState }
            .toObservable()
            .observeOn(schedulerRepository.ui())
            .startWithItem(CitiesLoadStageState.Start)
            .onErrorReturn { CitiesLoadStageState.Error(it) }

    private fun addCityStageState (cityModel: CityLoadModel) =
        citiesRepository
            .loadCity(cityModel)
            .subscribeOn(schedulerRepository.io())
            .map { CityAddStageState.Success(it) as CityAddStageState }
            .observeOn(schedulerRepository.ui())
            .toObservable()
            .startWithItem(CityAddStageState.Start)
            .onErrorReturn {error->
                val throwe = error
                CityAddStageState.Error("Ошибка при добавлении города") }
}