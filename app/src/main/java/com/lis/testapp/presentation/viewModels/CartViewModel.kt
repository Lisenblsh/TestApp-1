package com.lis.testapp.presentation.viewModels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.lis.domain.Repository
import com.lis.domain.StorePagingSource
import com.lis.domain.models.BasketModel
import kotlinx.coroutines.flow.Flow

class CartViewModel(
    private val repository: Repository,
): ViewModel() {

    val pagingStoreData: Flow<PagingData<BasketModel>>

    val delivery = MutableLiveData<String>("")
    val id = MutableLiveData<String>("")
    val total = MutableLiveData<Int>(0)

    init {
        pagingStoreData = getStoreData()
    }

    private fun getStoreData(): Flow<PagingData<BasketModel>> {

        val storePagingSource = StorePagingSource(repository)
        delivery.value = storePagingSource.delivery
        id.value = id.value
        total.value = storePagingSource.total


        return Pager(
            config = PagingConfig(
                pageSize = PAGE_SIZE,
                enablePlaceholders = false,
            )
        ){
            storePagingSource
        }.flow
    }

    companion object {
        private const val PAGE_SIZE = 20
    }
}