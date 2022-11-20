package com.lis.data.network.retrofit

import com.lis.domain.models.CartModel
import com.lis.domain.models.CurrentProduct
import com.lis.domain.models.StoreModel
import okhttp3.OkHttpClient
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

interface RetrofitService {

    @GET("654bd15e-b121-49ba-a588-960956b15175")
    suspend fun getStoreData():Response<StoreModel>

    @GET("6c14c560-15c6-4248-b9d2-b4508df7d4f5")
    suspend fun getCurrentProduct():Response<CurrentProduct>

    @GET("53539a72-3c5f-4f30-bbb1-6ca10d42c149")
    suspend fun getCart():Response<CartModel>


    companion object{
        private const val BASE_URL = "https://run.mocky.io/v3/"

        fun create(): RetrofitService {
            val okHttpClient = OkHttpClient().newBuilder()
                .retryOnConnectionFailure(true)
                .build()

            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(RetrofitService::class.java)
        }
    }
}