package com.ftgrl.cosmotica.ui.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ftgrl.cosmotica.data.entity.Product
import com.ftgrl.cosmotica.data.entity.User
import com.ftgrl.cosmotica.data.repository.ProductRepository
import com.ftgrl.cosmotica.data.repository.UserRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoriteViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val productRepository: ProductRepository
) : ViewModel() {

    private val db = FirebaseFirestore.getInstance()
    private val _favoriteProducts = MutableLiveData<List<Product>>()
    val favoriteProducts: LiveData<List<Product>> get() = _favoriteProducts

    fun getFavoriteProducts() {
        val user = FirebaseAuth.getInstance().currentUser
        val userId = user?.uid

        if (userId != null) {
            Log.d("FavoriteViewModel", "Kullanıcı ID: $userId")
            db.collection("users").document(userId)
                .get()
                .addOnSuccessListener { document ->
                    if (document != null && document.exists()) {
                        Log.d("FavoriteViewModel", "Belge başarıyla alındı.")
                        val user = document.toObject(User::class.java)
                        if (user != null) {
                            val favoriteProductIds = user.favorite
                            Log.d("FavoriteViewModel", "Favori Ürün ID'leri: $favoriteProductIds")
                            if (!favoriteProductIds.isNullOrEmpty()) {
                                fetchProductsByIds(favoriteProductIds)
                            } else {
                                Log.d("FavoriteViewModel", "Favori ürün bulunamadı.")
                            }
                        } else {
                            Log.d("FavoriteViewModel", "Kullanıcı nesnesi null döndü.")
                        }
                    } else {
                        Log.d("FavoriteViewModel", "Belge bulunamadı")
                    }
                }
                .addOnFailureListener { exception ->
                    Log.d("FavoriteViewModel", "Hata: ${exception.message}")
                }
        } else {
            Log.d("FavoriteViewModel", "Kullanıcı oturum açmamış.")
        }
    }

    private fun fetchProductsByIds(productIds: List<Int>) {
        viewModelScope.launch {
            val products = productIds.mapNotNull { productId ->
                try {
                    val product = productRepository.getProductById(productId.toLong())
                    Log.d("FavoriteViewModel", "Ürün: ${product.title}, ID: ${product.id}, Fiyat: ${product.price}")
                    product
                } catch (e: Exception) {
                    Log.e("FavoriteViewModel", "Ürün alınamadı: $e")
                    null
                }
            }
            _favoriteProducts.postValue(products) // LiveData'ya veri ataması
        }
    }
}

