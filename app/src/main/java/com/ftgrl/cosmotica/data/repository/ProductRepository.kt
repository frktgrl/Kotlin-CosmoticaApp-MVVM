package com.ftgrl.cosmotica.data.repository

import android.util.Log
import com.ftgrl.cosmotica.data.entity.Product
import com.ftgrl.cosmotica.data.entity.response.CartResponse
import com.ftgrl.cosmotica.data.entity.response.ProductResponse
import com.ftgrl.cosmotica.retrofit.ProductService
import javax.inject.Inject


class ProductRepository @Inject constructor (private val productService: ProductService) {



    suspend fun getAllProduct(): ProductResponse {
        return productService.getAllProduct()
    }

    suspend fun getProductsByCategory(categoryName: String): ProductResponse {
        return productService.getProductsByCategory(categoryName)
    }

    suspend fun getProductById(productId: Long): Product {
        return productService.getProductById(productId) // API'den Product nesnesi döner
    }

    suspend fun getAllCart(): CartResponse{
        return productService.getAllCart()
    }

    suspend fun getSearchProduct(query : String): ProductResponse {

        return productService.getSearchProduct(query)
    }



    fun searchProduct(query : String){

        Log.e("Search Text",query)

    }


}









