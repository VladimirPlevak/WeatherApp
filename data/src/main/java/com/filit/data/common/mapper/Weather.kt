package com.filit.data.common.mapper

import com.filit.data.apimodel.DayWeatherForecastModel
import com.filit.data.apimodel.WeatherApiModel
import com.filit.data.apimodel.WeatherForecastApiModel
import com.filit.domain.model.WeatherForecastModel
import com.filit.domain.model.WeatherModel
import io.reactivex.rxjava3.functions.BiFunction
import io.reactivex.rxjava3.functions.Function
import kotlin.math.roundToInt

class WeatherMapper: Function<WeatherApiModel, WeatherModel>{
    override fun apply(apiModel: WeatherApiModel): WeatherModel =
        WeatherModel(
            city = apiModel.name,
            date = apiModel.dt*1000,
            temperature = (apiModel.main.temperature.roundToInt().toString() + "\u2103"),
            tempFeelsLike = "Feels like ${apiModel.main.feelsLike.roundToInt()} \u2103",
            wind = "${apiModel.wind.speed.roundToInt()} m/s",
            humidity = "${apiModel.main.humidity.roundToInt()}%",
            pressure = "${apiModel.main.pressure.toString()} mmHg",
            cloudy_URL = apiModel.weather.first().icon
        )

}

class WeatherForecastMapper: BiFunction<WeatherForecastApiModel, WeatherApiModel, WeatherModel> {
    private val forecastMapper by lazy {ForecastMapper()}
    override fun apply(weatherForecastApiModel: WeatherForecastApiModel, weatherApiModel: WeatherApiModel ): WeatherModel =
        WeatherModel(
            city = weatherApiModel.name,
            date = weatherApiModel.dt*1000,
            temperature = (weatherApiModel.main.temperature.roundToInt().toString()+ "\u2103"),
            tempFeelsLike = "Feels like ${weatherApiModel.main.feelsLike.roundToInt()} \u2103",
            wind = "${weatherApiModel.wind.speed.roundToInt()} m/s",
            humidity = "${weatherApiModel.main.humidity.roundToInt()}%",
            pressure = "${weatherApiModel.main.pressure.toString()} mmHg",
            cloudy_URL = weatherApiModel.weather.first().icon,
            weatherForecastList = weatherForecastApiModel.daily.let { forecastMapper.apply(it) }
        )

}

class ForecastMapper: Function<List<DayWeatherForecastModel>, List<WeatherForecastModel>>{
    override fun apply(list: List<DayWeatherForecastModel>?): List<WeatherForecastModel> {
        val weatherForecastModelList = mutableListOf<WeatherForecastModel>()
        list?.forEach {
            weatherForecastModelList.add(
                WeatherForecastModel(
                    date = it.dt*1000,
                    minTemp = "${it.temp.min.roundToInt()}\u2103",
                    maxTemp = "${it.temp.max.roundToInt()}\u2103",
                    imageUrl = it.weather.first().icon
                )
            )
        }
        return weatherForecastModelList
    }
}
