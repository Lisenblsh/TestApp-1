package com.lis.domain

import com.lis.domain.models.CartModel
import com.lis.domain.models.CurrentProduct
import com.lis.domain.models.StoreModel
import retrofit2.Response

interface Repository {

    suspend fun getStoreData(): Response<StoreModel>

    suspend fun getCurrentProduct(): Response<CurrentProduct>

    suspend fun getCart(): Response<CartModel>
}