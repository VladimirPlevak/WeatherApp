package com.filit.testweather.di

import com.filit.data.repositoryImpl.*
import com.filit.domain.repository.*
import org.koin.dsl.module

val repositoryModule = module {
    single<RxRepository> { RxRepositoryImpl() }
    single<SchedulerRepository> { SchedulerRepositoryImpl() }
    single<WeatherRepository> { WeatherRepositoryImpl(get()) }
    single<CitiesRepository> { CitiesRepositoryImpl(get()) }
    single<AppRepository> { AppRepositoryImpl(get()) }
}