package com.ftgrl.cosmotica.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.ftgrl.cosmotica.data.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor (var repository: UserRepository): ViewModel() {


    fun signUp(email: String, password: String, onSuccess: () -> Unit, onFailure: (Exception) -> Unit) {

        repository.signUpUser(email, password,onSuccess, onFailure)
    }

}