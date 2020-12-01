package com.filit.data.repositoryImpl

import com.filit.data.BuildConfig
import com.filit.data.WeatherApi
import com.filit.data.common.mapper.CityWeatherMapper
import com.filit.domain.model.CityModel
import com.filit.domain.repository.CitiesRepository
import io.reactivex.rxjava3.core.Single


class CitiesRepositoryImpl(
    private val api: WeatherApi
): CitiesRepository {
    private val cityWeatherMapper by lazy { CityWeatherMapper() }
    private val remoteServiceAppId = BuildConfig.OPEN_WEATHER_API_KEY

    override fun loadCitiesRemote(listCities: List<String>): Single<List<CityModel>> {
        val listObservable = listCities
            .map { loadCityRemote(it) }
               return Single.zip(listObservable){
                    return@zip it.toList() as List<CityModel>
                }
    }

    override fun loadCityRemote(city: String): Single<CityModel> =
        api.loadWeather(cityName = city, remoteServiceAppId = remoteServiceAppId)
            .map(cityWeatherMapper)

}