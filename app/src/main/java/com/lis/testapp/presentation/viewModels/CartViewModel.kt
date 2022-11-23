package com.lis.testapp.presentation.viewModels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lis.domain.HttpException
import com.lis.domain.models.CartModel
import com.lis.domain.userCase.GetCartData
import kotlinx.coroutines.launch

class CartViewModel(
    private val getCartData: GetCartData
) : ViewModel() {

    val cartData = MutableLiveData<CartModel>()

    init {
        getCartData()
    }

    fun getCartData() = viewModelScope.launch {
        try {
            getCartData.execute()?.also {
                cartData.value = it
            }
        } catch (e: HttpException) {
            Log.e("viewModelEx", e.errorMessage)
        } catch (e: Exception) {
            Log.e("viewModelEx2", e.message.toString())
        }
    }
}