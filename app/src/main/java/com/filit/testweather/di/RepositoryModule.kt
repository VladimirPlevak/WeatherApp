package com.filit.testweather.di

import com.filit.data.repositoryImpl.CitiesRepositoryImpl
import com.filit.data.repositoryImpl.RxRepositoryImpl
import com.filit.data.repositoryImpl.SchedulerRepositoryImpl
import com.filit.data.repositoryImpl.WeatherRepositoryImpl
import com.filit.domain.repository.CitiesRepository
import com.filit.domain.repository.RxRepository
import com.filit.domain.repository.SchedulerRepository
import com.filit.domain.repository.WeatherRepository
import org.koin.dsl.module

val repositoryModule = module {
    single<RxRepository> { RxRepositoryImpl() }
    single<SchedulerRepository> { SchedulerRepositoryImpl() }
    single<WeatherRepository> { WeatherRepositoryImpl(get()) }
    single<CitiesRepository> { CitiesRepositoryImpl(get()) }
}