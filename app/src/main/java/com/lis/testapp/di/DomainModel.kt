package com.lis.testapp.di

import com.lis.domain.userCase.GetCurrentProduct
import org.koin.dsl.module

val domainModule = module {
    factory<GetCurrentProduct> {
        GetCurrentProduct(repository = get())
    }
}