package com.ftgrl.cosmotica.data.entity.response

import com.ftgrl.cosmotica.data.entity.Cart

data class CartResponse(
    val carts: List<Cart>,
    val total: Int,
    val skip: Int,
    val limit: Int
)