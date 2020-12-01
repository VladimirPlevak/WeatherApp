package com.filit.domain.repository

import com.filit.domain.model.CityLoadModel
import com.filit.domain.model.CityModel
import com.filit.domain.model.WeatherLoadModel
import com.filit.domain.model.WeatherModel
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single

interface CitiesRepository {
     fun loadCitiesRemote(listCities: List<String>): Single<List<CityModel>>
     fun loadCityRemote(city: String): Single<CityModel>
}