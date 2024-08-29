package com.ftgrl.cosmotica.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.navigation.fragment.findNavController
import com.ftgrl.cosmotica.MainActivity
import com.ftgrl.cosmotica.R
import com.ftgrl.cosmotica.data.entity.User
import com.ftgrl.cosmotica.databinding.FragmentSignUpBinding
import com.ftgrl.cosmotica.ui.viewmodel.SignUpViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class SignUpFragment : Fragment() {

    private lateinit var binding: FragmentSignUpBinding

    private lateinit var viewModel: SignUpViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_sign_up, container, false)
        binding.signUpFragment = this

        binding.loginText.setOnClickListener {
            findNavController().navigate(R.id.action_signUpFragment_to_signInFragment)
        }

        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val tempViewModel: SignUpViewModel by viewModels()

        viewModel = tempViewModel

    }


    fun signUp(email: String, password: String) {
        viewModel.signUp(email, password,
            onSuccess = {

                val intent = Intent(requireActivity(), MainActivity::class.java)
                startActivity(intent)
                requireActivity().finish()
            },
            onFailure = { exception ->

                Toast.makeText(context, "Fail: ${exception.message}", Toast.LENGTH_SHORT).show()
            }
        )
    }

}

