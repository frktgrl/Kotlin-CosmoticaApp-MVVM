package com.ftgrl.cosmotica.retrofit

import com.ftgrl.cosmotica.data.entity.Category
import retrofit2.http.GET

interface CategoryService {

    @GET("products/categories")
    suspend fun getAllCategories() : List<Category>
}