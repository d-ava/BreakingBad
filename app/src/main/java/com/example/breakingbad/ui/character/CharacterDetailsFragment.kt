package com.example.breakingbad.ui.character

import androidx.activity.OnBackPressedCallback
import androidx.navigation.NavArgs
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.breakingbad.databinding.FragmentCharacterDetailsBinding

import com.example.breakingbad.ui.BaseFragment
import com.squareup.picasso.Picasso


class CharacterDetailsFragment :
    BaseFragment<FragmentCharacterDetailsBinding>(FragmentCharacterDetailsBinding::inflate) {

    private val args: CharacterDetailsFragmentArgs by navArgs()


    override fun start() {

        setListeners()
        onBackPressed()

        setCharacterInformation()
    }

    private fun setCharacterInformation() {

        val character = args.bbCharacterInformation
        Picasso.get().load(character.img).into(binding.ivCharacter)
        binding.apply {
            tvName.text = character.name
            tvBirthday.text = character.birthday
            tvNickname.text = character.nickname.uppercase()
            tvPortrayed.text = character.portrayed
            tvStatus.text = character.status
            tvOccupation.text = "something \n\nsomething2 \n\nsomething3 \n\nsomething4 "
        }

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