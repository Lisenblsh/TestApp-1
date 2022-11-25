package com.lis.domain.models

data class CartModel(
    val basket: List<BasketModel>,
    val delivery: String,
    val id: String,
    val total: Int
)

data class BasketModel(
    val id: Int,
    val images: String,
    val price: Int,
    val title: String,
    var count: Int
)