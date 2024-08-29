package com.ftgrl.cosmotica.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.ftgrl.cosmotica.data.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor (var repository: UserRepository): ViewModel() {
}