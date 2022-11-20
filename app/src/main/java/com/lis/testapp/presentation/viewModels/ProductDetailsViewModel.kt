package com.lis.testapp.presentation.viewModels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.lis.domain.models.CurrentProduct
import com.lis.domain.userCase.GetCurrentProduct

class ProductDetailsViewModel(private val getProduct: GetCurrentProduct): ViewModel() {

    val productInfo: MutableLiveData<CurrentProduct>? = null

    suspend fun getProductInfo(): MutableLiveData<CurrentProduct>? {
        productInfo?.value = getProduct.execute()
        Log.e("productInfo", "${productInfo?.value}")
        return productInfo
    }
}