package com.lis.domain.userCase

import com.lis.domain.HttpException
import com.lis.domain.Repository
import com.lis.domain.models.CurrentProduct

class GetCurrentProduct(private val repository: Repository) {

    suspend fun execute(): CurrentProduct? {
        return checkProduct()
    }

    private suspend fun checkProduct(): CurrentProduct? {
        val response = repository.getCurrentProduct()
        if(response.isSuccessful) {
             return response.body()
        } else {
            throw HttpException(response.message())
        }
    }


}