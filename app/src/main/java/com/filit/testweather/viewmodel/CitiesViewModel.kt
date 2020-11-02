package com.filit.testweather.viewmodel

import androidx.lifecycle.MutableLiveData
import com.filit.domain.model.CityLoadModel
import com.filit.domain.model.CityModel
import com.filit.domain.model.WeatherLoadModel
import com.filit.domain.usecase.*
import com.filit.testweather.BuildConfig
import com.filit.testweather.common.extension.SingleLiveEventOnce

class CitiesViewModel(
    private val citiesUseCases: CitiesUseCases
) : BaseViewModel() {
    private val cityModelList = mutableListOf<CityModel>()
    val contentVisibilityState = MutableLiveData<ViewVisibilityState>()
    val loadProgressVisibilityState = MutableLiveData<ViewVisibilityState>()
    val errorState = MutableLiveData<ErrorState>()
    val errorAddCity = MutableLiveData<String>()
    val citesState = MutableLiveData<MutableList<CityModel>>()
    val openWeather = SingleLiveEventOnce<CityModel>()

    init {
        initLoadState()
    }

    fun loadCities(cityModelList: List<CityLoadModel>) = citiesUseCases.loadCitiesAction(
        CitiesAction.LoadCitiesAction(cityModelList)
    )

    fun addCities(model: CityLoadModel) = citiesUseCases.loadCitiesAction(
        CitiesAction.AddCityAction(model)
    )

    fun clickOnCity(position: Int) = cityModelList.getOrNull(position)
        .let {
            openWeather.value = it
        }
    private fun initLoadState() {
        disposables.add(
            citiesUseCases
                .loadState()
                .subscribe {
                    when(it) {
                       is CitiesLoadState.LoadCitiesState ->
                           handleLoadState(it.citiesLoadStageState)
                        is CitiesLoadState.AddCityState ->
                            handleAddState(it.cityAddStageState)
                    }
                }
        )
    }

    private fun handleLoadState(state: CitiesLoadStageState) {
        when (state) {
            CitiesLoadStageState.Start -> {
                contentVisibilityState.value = ViewVisibilityState.Invisible
                loadProgressVisibilityState.value = ViewVisibilityState.Visible
            }
            is CitiesLoadStageState.Error -> {
                loadProgressVisibilityState.value = ViewVisibilityState.Invisible
                contentVisibilityState.value = ViewVisibilityState.Visible
                errorState.value = ErrorState(state.error)
            }
            is CitiesLoadStageState.Success -> {
                loadProgressVisibilityState.value = ViewVisibilityState.Invisible
                contentVisibilityState.value = ViewVisibilityState.Visible
                citesState.value = state.cities as MutableList<CityModel>
                cityModelList.addAll(state.cities)
            }
        }
    }

    private fun handleAddState(state: CityAddStageState) {
        when (state) {
            CityAddStageState.Start -> {
                contentVisibilityState.value = ViewVisibilityState.Invisible
                loadProgressVisibilityState.value = ViewVisibilityState.Visible
            }
            is CityAddStageState.Error -> {
                loadProgressVisibilityState.value = ViewVisibilityState.Invisible
                contentVisibilityState.value = ViewVisibilityState.Visible
                errorAddCity.value = state.error
            }
            is CityAddStageState.Success -> {
                loadProgressVisibilityState.value = ViewVisibilityState.Invisible
                contentVisibilityState.value = ViewVisibilityState.Visible
                if (!cityModelList.map { it.city }.contains(state.city.city)) {
                    cityModelList.add(state.city)
                    citesState.value = cityModelList
                }else{
                    errorAddCity.value = "Этот город уже добавлен"
                }

            }
        }
    }
}