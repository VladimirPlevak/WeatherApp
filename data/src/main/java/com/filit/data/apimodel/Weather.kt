package com.filit.data.apimodel

import com.google.gson.annotations.SerializedName

data class WeatherApiModel (
@SerializedName(value = "coord") val coordinate: Coordinate,
@SerializedName(value = "weather") val weather: List<Weather>,
@SerializedName(value = "base") val base: String,
@SerializedName(value = "main") val main: Temperature,
@SerializedName(value = "visibility") val visibility: Int,
@SerializedName(value = "wind") val wind: Wind,
@SerializedName(value = "clouds") val clouds: Clouds,
@SerializedName(value = "dt") val dt: Long,
@SerializedName(value = "sys") val sys: Sys,
@SerializedName(value = "timezone") val timezone: Int,
@SerializedName(value = "id") val id: Int,
@SerializedName(value = "name") val name: String,
@SerializedName(value = "cod") val cod: Int
)

data class Coordinate(
    @SerializedName(value = "lon") val lon: Double,
    @SerializedName(value = "lat") val lat: Double
)
data class Weather(
    @SerializedName(value = "id") val id: Int,
    @SerializedName(value = "main") val main: String,
    @SerializedName(value = "description") val description: String,
    @SerializedName(value = "icon") val icon: String
)
data class Temperature(
    @SerializedName(value = "temp") val temperature: Double,
    @SerializedName(value = "feels_like") val feelsLike: Double,
    @SerializedName(value = "temp_min") val tempMin: Double,
    @SerializedName(value = "temp_max") val tempMax: Double,
    @SerializedName(value = "pressure") val pressure: Int,
    @SerializedName(value = "humidity") val humidity: Double
)
data class Wind(
    @SerializedName(value = "speed") val speed: Double,
    @SerializedName(value = "deg") val deg: Int
)
data class Clouds(
    @SerializedName(value = "all") val all: Int
)
data class Sys(
    @SerializedName(value = "type") val type: Int,
    @SerializedName(value = "id") val id: Int,
    @SerializedName(value = "message") val message: Double,
    @SerializedName(value = "country") val country: String,
    @SerializedName(value = "sunrise") val sunrise: Long,
    @SerializedName(value = "sunset") val sunset: Long
)