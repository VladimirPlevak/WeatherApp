package com.filit.domain.repository

import io.reactivex.rxjava3.core.Scheduler

interface SchedulerRepository {
    fun io(): Scheduler
    fun ui(): Scheduler
}