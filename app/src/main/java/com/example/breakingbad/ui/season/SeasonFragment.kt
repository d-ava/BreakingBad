package com.example.breakingbad.ui.season

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.breakingbad.R
import com.example.breakingbad.databinding.FragmentSeasonBinding
import com.example.breakingbad.ui.BaseFragment


class SeasonFragment : BaseFragment<FragmentSeasonBinding>(FragmentSeasonBinding::inflate) {
    override fun start() {
        setListeners()
    }

    private fun setListeners() {
        binding.backArrow.setOnClickListener {
            findNavController().popBackStack()
        }
    }
}