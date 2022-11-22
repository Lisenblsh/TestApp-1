package com.lis.data.repository

import com.lis.data.network.retrofit.RetrofitService
import com.lis.domain.Repository
import com.lis.domain.models.CartModel
import com.lis.domain.models.CurrentProduct
import com.lis.domain.models.StoreModel
import retrofit2.Response

class RepositoryImpl(private val service: RetrofitService): Repository {
    override suspend fun getStoreData(): Response<StoreModel> {
        return service.getStoreData()
    }

    override suspend fun getCurrentProduct(): Response<CurrentProduct> {
        return service.getCurrentProduct()
    }

    override suspend fun getCart(): Response<CartModel> {
        return service.getCart()
    }
}