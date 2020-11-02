package com.filit.data.common.mapper

import com.filit.data.apimodel.WeatherApiModel
import com.filit.domain.model.CityLoadModel
import com.filit.domain.model.CityModel
import io.reactivex.rxjava3.functions.Function
import kotlin.math.roundToInt

class CityWeatherMapper:Function<WeatherApiModel, CityModel>{
    override fun apply(apiModel: WeatherApiModel): CityModel=
        CityModel(
            city = apiModel.name,
            temperature = (apiModel.main.temperature.roundToInt().toString() + "\u2103"),
            country = apiModel.sys.country,
            cloudy_URL = apiModel.weather.first().icon
        )
}