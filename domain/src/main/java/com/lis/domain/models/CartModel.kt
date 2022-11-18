package com.lis.domain.models

data class CartModel(
    val basket: List<Basket>,
    val delivery: String,
    val id: String,
    val total: Int
)

data class Basket(
    val id: Int,
    val images: String,
    val price: Int,
    val title: String
)