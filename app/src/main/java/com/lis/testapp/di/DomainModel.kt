package com.lis.testapp.di

import com.lis.domain.userCase.GetCartData
import com.lis.domain.userCase.GetCurrentProduct
import com.lis.domain.userCase.GetStoreData
import org.koin.dsl.module

val domainModule = module {
    factory<GetCurrentProduct> {
        GetCurrentProduct(repository = get())
    }

    factory<GetCartData> {
        GetCartData(repository = get())
    }

    factory<GetStoreData> {
        GetStoreData(repository = get())
    }
}