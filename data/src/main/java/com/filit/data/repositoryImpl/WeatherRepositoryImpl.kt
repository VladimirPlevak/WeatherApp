package com.filit.data.repositoryImpl

import com.filit.data.WeatherApi
import com.filit.data.common.mapper.WeatherForecastMapper
import com.filit.data.common.mapper.WeatherMapper
import com.filit.domain.model.WeatherLoadModel
import com.filit.domain.model.WeatherModel
import com.filit.domain.repository.WeatherRepository
import io.reactivex.rxjava3.core.Single

class WeatherRepositoryImpl(
    private val api: WeatherApi
) : WeatherRepository {

    private val weatherForecastMapper by lazy { WeatherForecastMapper() }

    override fun loadWeatherForecast(model: WeatherLoadModel): Single<WeatherModel> =
        api.loadWeather(cityName = model.city, remoteServiceAppId = model.remoteServiceAppId)
            .flatMap {weatherApiModel->
                api.loadForecastWeather(lat = weatherApiModel.coordinate.lat,lon = weatherApiModel.coordinate.lon, remoteServiceAppId = model.remoteServiceAppId)
                    .flatMap { Single.just(weatherForecastMapper.apply(it, weatherApiModel))}

            }

}