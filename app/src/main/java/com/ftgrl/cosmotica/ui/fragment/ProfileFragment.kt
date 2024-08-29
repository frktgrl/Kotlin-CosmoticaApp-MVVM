package com.ftgrl.cosmotica.ui.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import com.ftgrl.cosmotica.R
import com.ftgrl.cosmotica.data.entity.User
import com.ftgrl.cosmotica.databinding.FragmentProfileBinding
import com.ftgrl.cosmotica.ui.viewmodel.ProfileViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class ProfileFragment : Fragment() {

    private lateinit var binding: FragmentProfileBinding

    private lateinit var viewModel: ProfileViewModel

    private val db = FirebaseFirestore.getInstance()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_profile, container, false)
        binding.profileFragment = this

        fetchUserData()

        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val tempViewModel : ProfileViewModel by viewModels()
        viewModel = tempViewModel

    }


    private fun fetchUserData() {
        val user = FirebaseAuth.getInstance().currentUser
        val userId = user?.uid

        if (userId != null) {
            db.collection("users").document(userId)
                .get()
                .addOnSuccessListener { document ->
                    if (document != null && document.exists()) {
                        val user = document.toObject(User::class.java)
                        if (user != null) {
                            binding.user = user // Kullanıcı verilerini DataBinding'e aktar
                        }
                    } else {
                        Log.d("ProfileFragment", "Belge bulunamadı")
                    }
                }
                .addOnFailureListener { exception ->
                    Log.d("ProfileFragment", "Hata: ${exception.message}")
                }
        } else {
            Log.d("ProfileFragment", "Kullanıcı oturum açmamış.")
        }
    }
}
