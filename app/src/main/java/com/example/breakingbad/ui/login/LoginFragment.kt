package com.example.breakingbad.ui.login

import android.util.Log
import androidx.navigation.fragment.findNavController
import com.example.breakingbad.databinding.FragmentLoginBinding
import com.example.breakingbad.ui.BaseFragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase


class LoginFragment : BaseFragment<FragmentLoginBinding>(FragmentLoginBinding::inflate) {

    lateinit var auth: FirebaseAuth

    override fun start() {
        auth = Firebase.auth

        setListeners()
    }

    private fun logInUser() {

        auth.signInWithEmailAndPassword(
            binding.etEmail.text.toString(),
            binding.etPassword.text.toString()
        ).addOnCompleteListener(requireActivity()) {
            if (it.isSuccessful){
                Log.d("---", "login successfull")

            }else{
                Log.d("---", "problem with login")
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