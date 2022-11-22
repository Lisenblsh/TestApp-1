package com.lis.testapp.presentation.viewModels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lis.domain.Repository
import com.lis.domain.models.BestSellerModel
import com.lis.domain.models.CategoryModel
import com.lis.domain.models.HomeStoreModel
import com.lis.testapp.R
import kotlinx.coroutines.launch

class ExplorerViewModel(
    private val repository: Repository
) : ViewModel() {

    val hotSales = MutableLiveData<List<HomeStoreModel>>()
    val bestSeller = MutableLiveData<List<BestSellerModel>>()
    val category = MutableLiveData<List<CategoryModel>>()

    init {
        getStoreData()
        getCategoryList()
    }

    private fun getStoreData() = viewModelScope.launch {
        val response = repository.getStoreData()
        if(response.isSuccessful){
            response.body()?.also {
                hotSales.value = it.home_store
                bestSeller.value = it.best_seller
            }
        }
    }

    private fun getCategoryList() = viewModelScope.launch {
        category.value = listOf<CategoryModel>(
            CategoryModel(R.drawable.icon_phone_category, "Phone"),
            CategoryModel(R.drawable.icon_computer_category, "Computer"),
            CategoryModel(R.drawable.icon_health_category, "Health"),
            CategoryModel(R.drawable.icon_books_category, "Books"),
            CategoryModel(0, "Smth"),
        )//тестовый лист
    }



}