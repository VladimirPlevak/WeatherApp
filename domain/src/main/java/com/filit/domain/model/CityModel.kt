package com.filit.domain.model

data class CityModel(
    val city: String ="",
    val temperature: String = "",
    val country: String = "",
    val cloudy_URL: String = ""
)

data class CityLoadModel(
    val city: String,
    val remoteServiceAppId: String
)