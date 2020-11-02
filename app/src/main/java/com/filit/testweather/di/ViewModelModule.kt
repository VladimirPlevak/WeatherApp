package com.filit.testweather.di

import com.filit.domain.model.WeatherLoadModel
import com.filit.testweather.viewmodel.CitiesViewModel
import com.filit.testweather.viewmodel.WeatherViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { WeatherViewModel(get())}
    viewModel { CitiesViewModel(get()) }
}