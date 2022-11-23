package com.lis.testapp.presentation.viewModels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lis.domain.HttpException
import com.lis.domain.models.CurrentProduct
import com.lis.domain.userCase.GetCurrentProduct
import kotlinx.coroutines.launch

class ProductDetailsViewModel(private val getProduct: GetCurrentProduct): ViewModel() {

    val productInfo: MutableLiveData<CurrentProduct?> = MutableLiveData()

    init {
        getProductInfo()
    }

    fun getProductInfo() = viewModelScope.launch {
        try {
            productInfo.value = getProduct.execute()
        }catch (e: HttpException){
            Log.e("viewModelEx",e.errorMessage)
        } catch (e: Exception){
            Log.e("viewModelEx2", e.message.toString())
        }
    }
}