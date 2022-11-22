package com.lis.domain.models

data class StoreModel(
    val home_store: List<HomeStoreModel>,
    val best_seller: List<BestSellerModel>
)

data class HomeStoreModel(
    val id: Int,
    val is_buy: Boolean,
    val is_new: Boolean = false,
    val picture: String,
    val subtitle: String,
    val title: String
)

data class BestSellerModel(
    val discount_price: Int,
    val id: Int,
    val is_favorites: Boolean,
    val picture: String,
    val price_without_discount: Int,
    val title: String
)