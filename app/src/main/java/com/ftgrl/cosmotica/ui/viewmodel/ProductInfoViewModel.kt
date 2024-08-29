package com.ftgrl.cosmotica.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ftgrl.cosmotica.data.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ProductInfoViewModel @Inject constructor(private val userRepository: UserRepository) : ViewModel() {


    fun addFavorite(productId: Long) {
        userRepository.addFavoriteProduct(productId)
    }
}
