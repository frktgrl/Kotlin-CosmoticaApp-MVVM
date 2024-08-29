package com.ftgrl.cosmotica.data.repository

import com.ftgrl.cosmotica.data.entity.Category
import com.ftgrl.cosmotica.retrofit.CategoryService
import javax.inject.Inject


class CategoryRepository @Inject constructor(private val categoryService: CategoryService) {

    suspend fun getCategories(): List<Category> {
        return categoryService.getAllCategories()
    }
}