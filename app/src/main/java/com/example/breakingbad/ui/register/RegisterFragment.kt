package com.example.breakingbad.ui.register

import android.annotation.SuppressLint
import android.util.Log
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.breakingbad.databinding.FragmentRegisterBinding
import com.example.breakingbad.extensions.makeSnackbar
import com.example.breakingbad.model.User
import com.example.breakingbad.ui.BaseFragment
import com.example.breakingbad.util.Resource
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class RegisterFragment : BaseFragment<FragmentRegisterBinding>(FragmentRegisterBinding::inflate) {

    private val viewModel: RegisterViewModel by viewModels()

    override fun start() {
        setListeners()


    }


    @SuppressLint("UnsafeRepeatOnLifecycleDetector")
    private fun registerUser() {
        val name = binding.etName.text.toString()
        val email = binding.etEmail.text.toString()
        val password = binding.etPassword.text.toString()
        val repeatPassword = binding.etRepeatPassword.text.toString()

        viewModel.registerUser(
            name, email, password, repeatPassword
        )

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.userRegister.collect {
                    when (it) {
                        is Resource.Loading -> {
                            showLoading()
                        }
                        is Resource.Success -> {
                            hideLoading()
                            view?.makeSnackbar("yay")
                        }
                        is Resource.Error -> {
                            hideLoading()

                            view?.makeSnackbar("${it.message}")
                        }

                    }
                }
            }
        }

    }


    private fun setListeners() {
        binding.apply {
            backArrow.setOnClickListener {
                findNavController().popBackStack()
            }
            btnLogin.setOnClickListener {
                registerUser()
            }


        }
    }
}