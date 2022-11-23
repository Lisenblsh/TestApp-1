package com.lis.domain.userCase

import com.lis.domain.HttpException
import com.lis.domain.Repository
import com.lis.domain.models.CartModel

class GetCartData(
    private val repository: Repository
) {

    suspend fun execute(): CartModel? {
        return getCartData()
    }

    private suspend fun getCartData(): CartModel? {
        val response =repository.getCart()
        if(response.isSuccessful){
            return response.body()
        } else {
            throw HttpException(response.message())
        }
    }
}