package com.filit.data.apimodel

import com.google.gson.annotations.SerializedName

data class WeatherForecastApiModel(
    @SerializedName(value = "lat") val lat: Double,
    @SerializedName(value = "lon") val lon: Double,
    @SerializedName(value = "timezone") val timezone: String,
    @SerializedName(value = "timezone_offset") val timezone_offset: Int,
    @SerializedName(value = "daily") val daily: List<DayWeatherForecastModel>,
    @SerializedName(value = "current") val current: CurrentWeatherModel
)

data class DayWeatherForecastModel(
    @SerializedName(value = "dt") val dt: Long,
    @SerializedName(value = "temp") val temp: Temp,
    @SerializedName(value = "sunrise") val sunrise: Long,
    @SerializedName(value = "sunset") val sunset: Long,
    @SerializedName(value = "pressure") val pressure: Int,
    @SerializedName(value = "humidity") val humidity: Double,
    @SerializedName(value = "weather") val weather: List<Weather>
)

data class Temp(
    @SerializedName(value = "min") val min : Double ,
    @SerializedName(value = "max") val max : Double
)

data class CurrentWeatherModel (
    @SerializedName(value = "dt") val dt: Long,
    @SerializedName(value = "temp") val temperature: Double,
    @SerializedName(value = "feels_like") val feelsLike: Double,
    @SerializedName(value = "sunrise") val sunrise: Long,
    @SerializedName(value = "sunset") val sunset: Long,
    @SerializedName(value = "pressure") val pressure: Int,
    @SerializedName(value = "humidity") val humidity: Double,
    @SerializedName(value = "weather") val weather: List<Weather>,
    @SerializedName(value = "wind_speed") val wind_speed: Double
)