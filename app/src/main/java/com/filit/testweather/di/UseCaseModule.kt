package com.filit.testweather.di

import com.filit.domain.usecase.AppUseCases
import com.filit.domain.usecase.WeatherUseCases
import com.filit.domain.interactor.AppInteractor
import com.filit.domain.interactor.CitiesInteractor
import com.filit.domain.interactor.WeatherInteractor
import com.filit.domain.usecase.CitiesUseCases
import org.koin.dsl.module

val useCasesModule = module {
    factory<WeatherUseCases> { WeatherInteractor(get(), get(), get()) }
    factory<CitiesUseCases> { CitiesInteractor(get(), get(), get()) }
    factory<AppUseCases> { AppInteractor(get()) }
}