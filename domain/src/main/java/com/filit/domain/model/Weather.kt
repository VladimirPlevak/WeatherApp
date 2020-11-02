package com.filit.domain.model

data class WeatherModel(
    val city: String ="",
    val date: Long? = null,
    val temperature: String = "",
    val tempFeelsLike: String = "",
    val wind: String = "",
    val humidity: String = "",
    val pressure: String = "",
    val weatherForecastList: List<WeatherForecastModel>? = listOf(),
    val cloudy_URL: String = ""
)

data class WeatherLoadModel(
    var city: String,
    val remoteServiceAppId: String
)

data class WeatherForecastModel(
    val date: Long,
    val minTemp: String,
    val maxTemp: String,
    val imageUrl: String
)