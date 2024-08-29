package com.ftgrl.cosmotica.ui.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ftgrl.cosmotica.data.entity.Cart
import com.ftgrl.cosmotica.data.entity.Category
import com.ftgrl.cosmotica.data.entity.Product
import com.ftgrl.cosmotica.data.repository.CategoryRepository
import com.ftgrl.cosmotica.data.repository.ProductRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val categoryRepository: CategoryRepository,
    private val productRepository: ProductRepository
) : ViewModel() {

    // categoryList türünü List<Category> olarak koruyoruz
    private val _categoryList = MutableLiveData<List<Category>>()
    val categoryList: LiveData<List<Category>> get() = _categoryList

    val carts = MutableLiveData<List<Cart>>()
    val searchResults = MutableLiveData<List<Product>>()
    val errorMessage = MutableLiveData<String?>()

    fun fetchCategories() {
        viewModelScope.launch {
            try {
                val categoriesResponse = categoryRepository.getCategories()
                Log.d("CategoryViewModel", "Kategoriler: $categoriesResponse")

                // Doğrudan Category nesneleri ile güncelleyelim
                _categoryList.value = categoriesResponse
            } catch (e: Exception) {
                Log.e("CategoryViewModel", "Kategori verisi alınırken hata oluştu", e)
            }
        }
    }

    fun fetchCart() {
        viewModelScope.launch {
            try {
                val response = productRepository.getAllCart()
                carts.value = response.carts
            } catch (e: Exception) {
                errorMessage.value = "Ürünler getirilemedi: ${e.message}"
            }
        }
    }


    fun fetchSearchProduct(query: String) {
        viewModelScope.launch {
            try {
                val response = productRepository.getSearchProduct(query)
                searchResults.value = response.products
            } catch (e: Exception) {
                errorMessage.value = "Arama sırasında hata oluştu: ${e.message}"
            }
        }
    }

    fun searchProduct(query: String) {
        fetchSearchProduct(query)
    }
}

