package com.example.breakingbad.ui.login

import android.annotation.SuppressLint
import android.util.Log
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.example.breakingbad.R
import com.example.breakingbad.databinding.FragmentLoginBinding
import com.example.breakingbad.extensions.makeSnackbar
import com.example.breakingbad.ui.BaseFragment
import com.example.breakingbad.util.Resource
import com.example.breakingbad.util.Utils.auth
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LoginFragment : BaseFragment<FragmentLoginBinding>(FragmentLoginBinding::inflate) {

    private val viewmodel:LoginViewModel by viewModels()


    override fun start() {


        setListeners()
    }

    @SuppressLint("UnsafeRepeatOnLifecycleDetector")
    private fun logInUser(){
        val email = binding.etEmail.text.toString()
        val password = binding.etPassword.text.toString()
        viewmodel.logInUser(email, password)
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED){
                viewmodel.login.collect {
                    when(it){
                        is Resource.Loading -> {
                            showLoading()
                        }
                        is Resource.Success -> {
                            hideLoading()
//                            view?.makeSnackbar("login successfull uid= ${auth.uid}")
                            findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToNavHomeFragment())
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
            tvRegister.setOnClickListener {
                findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToRegisterFragment())
            }

            btnLogin.setOnClickListener {
                logInUser()
            }
        }
    }
}