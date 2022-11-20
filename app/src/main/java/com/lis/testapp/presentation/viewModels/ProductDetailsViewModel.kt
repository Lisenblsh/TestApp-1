package com.lis.testapp.presentation.viewModels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lis.domain.models.CurrentProduct
import com.lis.domain.userCase.GetCurrentProduct
import kotlinx.coroutines.launch

class ProductDetailsViewModel(private val getProduct: GetCurrentProduct): ViewModel() {

    val productInfo: MutableLiveData<CurrentProduct?> = MutableLiveData()

    suspend fun getProductInfo() = viewModelScope.launch {
        productInfo.value = getProduct.execute()
    }
}