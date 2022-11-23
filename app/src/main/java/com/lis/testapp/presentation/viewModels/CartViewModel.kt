package com.lis.testapp.presentation.viewModels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lis.domain.Repository
import com.lis.domain.models.CartModel
import kotlinx.coroutines.launch

class CartViewModel(
    private val repository: Repository,
): ViewModel() {

    val cartData = MutableLiveData<CartModel>()

    init {
        getCartData()
    }

    private fun getCartData() = viewModelScope.launch {
        val response = repository.getCart()
        if(response.isSuccessful){
            cartData.value = response.body()
        }


    }
}