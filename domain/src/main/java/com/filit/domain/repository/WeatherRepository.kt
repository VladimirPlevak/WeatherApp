package com.filit.domain.repository

import com.filit.domain.model.WeatherLoadModel
import com.filit.domain.model.WeatherModel
import io.reactivex.rxjava3.core.Single

interface   WeatherRepository {
    fun loadWeatherForecast(model: WeatherLoadModel): Single<WeatherModel>
}