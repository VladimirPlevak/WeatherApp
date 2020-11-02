package com.filit.data.repositoryImpl

import com.filit.domain.repository.SchedulerRepository
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Scheduler
import io.reactivex.rxjava3.schedulers.Schedulers


class SchedulerRepositoryImpl : SchedulerRepository {
    override fun io(): Scheduler = Schedulers.io()
    override fun ui(): Scheduler = AndroidSchedulers.mainThread()
}