package com.example.breakingbad.ui.character

import androidx.activity.OnBackPressedCallback
import androidx.navigation.NavArgs
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.breakingbad.databinding.FragmentCharacterDetailsBinding
import com.example.breakingbad.ui.BaseFragment


class CharacterDetailsFragment :
    BaseFragment<FragmentCharacterDetailsBinding>(FragmentCharacterDetailsBinding::inflate) {

    private val args: CharacterDetailsFragmentArgs by navArgs()


    override fun start() {

        setListeners()
        onBackPressed()

        setCharacterInformation()
    }

    private fun setCharacterInformation() {
        binding.tvName.text = args.bbCharacterInformation.nickname

    }

    private fun setListeners() {
        binding.backArrow.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun onBackPressed() {
        activity?.onBackPressedDispatcher?.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                findNavController().popBackStack()

            }
        })
    }
}