package com.filit.data.repositoryImpl

import com.filit.data.WeatherApi
import com.filit.data.common.mapper.CityWeatherMapper
import com.filit.domain.model.CityLoadModel
import com.filit.domain.model.CityModel
import com.filit.domain.repository.CitiesRepository
import io.reactivex.rxjava3.core.Single


class CitiesRepositoryImpl(
    private val api: WeatherApi
): CitiesRepository {
    private val cityWeatherMapper by lazy { CityWeatherMapper() }

    override fun loadCities(listCities: List<CityLoadModel>): Single<List<CityModel>> {
        val listObservable = listCities.map { loadCity(it) }
               return Single.zip(listObservable){
                    return@zip it.toList() as List<CityModel>
                }
    }

    override fun loadCity(city: CityLoadModel): Single<CityModel> =
        api.loadWeather(cityName = city.city, remoteServiceAppId = city.remoteServiceAppId)
            .map(cityWeatherMapper)

}