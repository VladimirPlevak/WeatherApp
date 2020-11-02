package com.filit.testweather.di

import com.filit.data.WeatherApi
import org.koin.dsl.module
import retrofit2.Retrofit

val apiModule = module {
    single { createApi(get()) }
}

private fun createApi(retrofit: Retrofit) = retrofit.create(WeatherApi::class.java)