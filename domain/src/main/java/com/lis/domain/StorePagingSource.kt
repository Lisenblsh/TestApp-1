package com.lis.domain

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.lis.domain.models.BasketModel
import com.lis.domain.models.CartModel
import retrofit2.HttpException
import retrofit2.Response

class StorePagingSource(private val repository: Repository) : PagingSource<Int, BasketModel>() {

    var delivery: String = ""
    var id: String = ""
    var total: Int = 0

    override fun getRefreshKey(state: PagingState<Int, BasketModel>): Int? {
        val anchorPosition = state.anchorPosition ?: return null
        val page = state.closestPageToPosition(anchorPosition) ?: return null
        return page.nextKey?.minus(1) ?: page.prevKey?.plus(1)
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, BasketModel> {
        val page = params.key ?: 1
        val pageSize = params.loadSize

        val response = getCart()
        return if (response.isSuccessful) {
            val body: CartModel = checkNotNull(response.body())
            delivery = body.delivery
            id = body.id
            total = body.total
            val storeData = body.basket
            val prevKey = if (page == 1) null else page - 1
            val nextKey = if (storeData.size < pageSize) null else page + 1
            LoadResult.Page(storeData, prevKey, nextKey)
        } else {
            LoadResult.Error(HttpException(response))
        }
    }

    private suspend fun getCart(): Response<CartModel> {
        return repository.getCart()
    }
}