package com.lis.testapp.presentation.viewModels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lis.domain.HttpException
import com.lis.domain.models.BestSellerModel
import com.lis.domain.models.CategoryModel
import com.lis.domain.models.HomeStoreModel
import com.lis.domain.userCase.GetStoreData
import com.lis.testapp.R
import kotlinx.coroutines.launch

class ExplorerViewModel(
    private val getStoreData: GetStoreData
) : ViewModel() {

    val hotSales = MutableLiveData<List<HomeStoreModel>>()
    val bestSeller = MutableLiveData<List<BestSellerModel>>()
    val category = MutableLiveData<List<CategoryModel>>()

    init {
        getStoreData()
        getCategoryList()
    }

    fun getStoreData() = viewModelScope.launch {
        try {
            getStoreData.execute()?.also {
                hotSales.value = it.home_store
                bestSeller.value = it.best_seller
            }
        }catch (e: HttpException){
            Log.e("viewModelEx",e.errorMessage)
        } catch (e: Exception){
            Log.e("viewModelEx2", e.message.toString())
        }
    }

    fun getCategoryList() = viewModelScope.launch {
        category.value = listOf<CategoryModel>(
            CategoryModel(R.drawable.icon_phone_category, "Phone"),
            CategoryModel(R.drawable.icon_computer_category, "Computer"),
            CategoryModel(R.drawable.icon_health_category, "Health"),
            CategoryModel(R.drawable.icon_books_category, "Books"),
            CategoryModel(0, "Smth"),
        )//тестовый лист
    }



}