package com.ftgrl.cosmotica.ui.viewmodel

import android.net.http.HttpException
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ftgrl.cosmotica.data.entity.Product
import com.ftgrl.cosmotica.data.repository.ProductRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CategoryViewModel @Inject constructor(private val repository: ProductRepository) : ViewModel() {

    val products = MutableLiveData<List<Product>>()
    val errorMessage = MutableLiveData<String?>()

    fun getProductsByCategory(categoryName: String) {
        viewModelScope.launch {
            try {
                val response = repository.getProductsByCategory(categoryName)
                products.value = response.products.map { product ->
                    Product(
                        id = product.id,
                        title = product.title,
                        description = product.description,
                        price = product.price,
                        discountPercentage = product.discountPercentage,
                        rating = product.rating,
                        stock = product.stock,
                        brand = product.brand,
                        category = product.category,
                        thumbnail = product.thumbnail,
                        images = product.images
                    )
                }
            }  catch (e: Exception) {
                // Handle other exceptions
            }
        }

    }

    fun getAllProduct() {
        viewModelScope.launch {
            try {
                val response = repository.getAllProduct()
                products.value = response.products
            } catch (e: Exception) {
                errorMessage.value = "Ürünler getirilemedi: ${e.message}"
            }
        }
    }




}
