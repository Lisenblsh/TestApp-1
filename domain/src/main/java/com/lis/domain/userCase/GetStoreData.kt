package com.lis.domain.userCase

import com.lis.domain.HttpException
import com.lis.domain.Repository
import com.lis.domain.models.StoreModel

class GetStoreData(private val repository: Repository) {
    suspend fun execute(): StoreModel? {
        return getCartData()
    }

    private suspend fun getCartData(): StoreModel? {
        val response = repository.getStoreData()
        if(response.isSuccessful){
            return response.body()
        } else {
            throw HttpException(response.message())
        }
    }
}