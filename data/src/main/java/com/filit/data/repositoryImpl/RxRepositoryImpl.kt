package com.filit.data.repositoryImpl

import com.filit.domain.repository.RxRepository
import io.reactivex.rxjava3.exceptions.UndeliverableException
import io.reactivex.rxjava3.plugins.RxJavaPlugins

import java.io.IOException

class RxRepositoryImpl : RxRepository {
    override fun init() {
        RxJavaPlugins.setErrorHandler { throwable ->
            when (val error = getError(throwable)) {
                is IOException,         // Network issue or API that throws on cancellation
                is InterruptedException // Blocking code was interrupted by a dispose call
                ->
                    Unit
                is VirtualMachineError, is ThreadDeath, is LinkageError,    // Error belongs to a set of "fatal" error varieties
                is NullPointerException, is IllegalArgumentException,       // Likely a bug in the application
                is IllegalStateException                                    // Likely a bug in RxJava or in a custom operator
                ->
                    Thread.currentThread().uncaughtExceptionHandler?.uncaughtException(
                        Thread.currentThread(),
                        error
                    )
            }
        }
    }

    private fun getError(throwable: Throwable) =
        if (throwable is UndeliverableException)
            throwable.cause
        else
            throwable

}