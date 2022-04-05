package com.example.breakingbad.ui.login

import androidx.navigation.fragment.findNavController
import com.example.breakingbad.databinding.FragmentLoginBinding
import com.example.breakingbad.ui.BaseFragment


class LoginFragment : BaseFragment<FragmentLoginBinding>(FragmentLoginBinding::inflate) {

    override fun start() {
        setListeners()
    }

    private fun setListeners(){
        binding.backArrow.setOnClickListener {
            findNavController().popBackStack()
        }
    }
}