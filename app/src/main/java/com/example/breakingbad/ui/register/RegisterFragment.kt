package com.example.breakingbad.ui.register

import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.breakingbad.databinding.FragmentRegisterBinding
import com.example.breakingbad.ui.BaseFragment

class RegisterFragment : BaseFragment<FragmentRegisterBinding>(FragmentRegisterBinding::inflate) {

    override fun start() {
        setListeners()
    }

    private fun setListeners(){
        binding.apply {
            backArrow.setOnClickListener {
                findNavController().popBackStack()
            }
        }
    }
}