package com.lis.domain.userCase

import com.lis.domain.Repository
import com.lis.domain.models.CurrentProduct

class GetCurrentProduct(private val repository: Repository) {

    suspend fun execute(): CurrentProduct? {
        return checkProduct()
    }

    private suspend fun checkProduct(): CurrentProduct? {
        val currentProduct = repository.getCurrentProduct()
        return if(currentProduct.isSuccessful) {
            currentProduct.body()
        } else {
            null
        }
    }


}