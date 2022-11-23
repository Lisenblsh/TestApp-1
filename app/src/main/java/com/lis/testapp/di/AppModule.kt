package com.lis.testapp.di

import com.lis.testapp.presentation.viewModels.CartViewModel
import com.lis.testapp.presentation.viewModels.ExplorerViewModel
import com.lis.testapp.presentation.viewModels.ProductDetailsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    viewModel<ProductDetailsViewModel> {
        ProductDetailsViewModel(getProduct = get())
    }

    viewModel<CartViewModel>{
        CartViewModel(getCartData = get())
    }

    viewModel<ExplorerViewModel>{
        ExplorerViewModel(getStoreData = get())
    }
}