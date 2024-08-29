package com.ftgrl.cosmotica.data.entity.response

import com.ftgrl.cosmotica.data.entity.Product

data class ProductResponse (
    val products: List<Product>,
    val total: Long,
    val skip: Long,
    val limit: Long
)
