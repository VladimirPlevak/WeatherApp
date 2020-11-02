package com.filit.domain.interactor

import com.filit.domain.repository.RxRepository
import com.filit.domain.usecase.AppUseCases

class AppInteractor(
    private val rxRepository: RxRepository
) : AppUseCases {
    override fun init() {
        rxRepository.init()
    }
}