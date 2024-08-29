package com.ftgrl.cosmotica.retrofit

import com.ftgrl.cosmotica.data.entity.Product
import com.ftgrl.cosmotica.data.entity.response.CartResponse
import com.ftgrl.cosmotica.data.entity.response.ProductResponse
import retrofit2.http.GET
import retrofit2.http.Path


interface ProductService {
    @GET("products")
    suspend fun getAllProduct(): ProductResponse

    @GET("products/category/{categoryName}")
    suspend fun getProductsByCategory(@Path("categoryName") categoryName: String): ProductResponse

    @GET("products/{productId}")
    suspend fun getProductById(@Path("productId") productId: Long): Product

    @GET("carts")
    suspend fun getAllCart(): CartResponse

    @GET("products/search?q={query}")
    suspend fun getSearchProduct(@Path("query") query : String) : ProductResponse
}
