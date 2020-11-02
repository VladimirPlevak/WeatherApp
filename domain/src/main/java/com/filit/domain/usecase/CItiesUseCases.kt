package com.filit.domain.usecase

import com.filit.domain.model.CityLoadModel
import com.filit.domain.model.CityModel
import com.filit.domain.model.WeatherLoadModel
import com.filit.domain.model.WeatherModel
import io.reactivex.rxjava3.core.Observable

interface CitiesUseCases {
    fun loadCitiesAction(action: CitiesAction)
    fun loadState(): Observable<CitiesLoadState>
}

sealed class CitiesLoadState {
    data class LoadCitiesState(val citiesLoadStageState: CitiesLoadStageState):CitiesLoadState()
    data class AddCityState(val cityAddStageState: CityAddStageState):CitiesLoadState()
}




sealed class CitiesAction {
    data class LoadCitiesAction(val cityList: List<CityLoadModel>): CitiesAction()
    data class AddCityAction(val city: CityLoadModel): CitiesAction()
}


sealed class CitiesLoadStageState {
    object Start : CitiesLoadStageState()
    data class Error(val error: Throwable) : CitiesLoadStageState()
    data class Success(val cities: List<CityModel>) : CitiesLoadStageState()
}

sealed class CityAddStageState {
    object Start : CityAddStageState()
    data class Error(val error: String) : CityAddStageState()
    data class Success(val city: CityModel) : CityAddStageState()
}