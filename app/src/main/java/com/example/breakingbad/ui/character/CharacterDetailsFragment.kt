package com.example.breakingbad.ui.character

import androidx.activity.OnBackPressedCallback
import androidx.navigation.fragment.findNavController
import com.example.breakingbad.databinding.FragmentCharacterDetailsBinding
import com.example.breakingbad.ui.BaseFragment


class CharacterDetailsFragment : BaseFragment<FragmentCharacterDetailsBinding>(FragmentCharacterDetailsBinding::inflate) {

    override fun start() {

        setListeners()
        onBackPressed()
    }

    private fun setListeners(){
        binding.backArrow.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun onBackPressed(){
        activity?.onBackPressedDispatcher?.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                findNavController().popBackStack()

            }
        })
    }
}