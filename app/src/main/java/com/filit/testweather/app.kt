package com.filit.testweather

import android.app.Application
import com.filit.domain.usecase.AppUseCases
import com.filit.testweather.di.*
import org.koin.android.ext.android.inject
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin


class App : Application() {

    private val appUseCases: AppUseCases by inject()

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@App)
            modules(
                listOf(
                    netModule,
                    apiModule,
                    repositoryModule,
                    useCasesModule,
                    viewModelModule
                )
            )
        }

        appUseCases.init()
    }
}