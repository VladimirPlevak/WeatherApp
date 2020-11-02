package com.filit.data

import com.filit.data.apimodel.WeatherApiModel
import com.filit.data.apimodel.WeatherForecastApiModel
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Query

@JvmSuppressWildcards
interface WeatherApi {

    @GET("data/2.5/weather")
    fun loadWeather(
        @Query("q") cityName: String,
        @Query("appid") remoteServiceAppId: String,
        @Query("units") units: String = "metric"
    ): Single<WeatherApiModel>


    @GET("data/2.5/onecall")
    fun loadForecastWeather(
        @Query("lat") lat: Double,
        @Query("lon") lon: Double,
        @Query("appid") remoteServiceAppId: String,
        @Query("lang") lang: String = "ru",
        @Query("units") units: String = "metric",
        @Query("exclude") excludeList: List<String> = listOf(
            "minutely",
            "hourly",
            "alerts"
        )
    ): Single<WeatherForecastApiModel>

}