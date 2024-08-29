package com.ftgrl.cosmotica.data.entity

data class Cart(
    val id: Int,
    val products: List<Product>,
    val total: Double,
    val discountedTotal: Double,
    val userId: Int,
    val totalProducts: Int,
    val totalQuantity: Int
)