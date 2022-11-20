package com.lis.testapp.di

import com.lis.data.network.retrofit.RetrofitService
import com.lis.data.repository.RepositoryImpl
import com.lis.domain.Repository
import org.koin.dsl.module

val dataModule = module {
    single<RetrofitService> {
        RetrofitService.create()
    }

    single<Repository> {
        RepositoryImpl(service = get())
    }
}