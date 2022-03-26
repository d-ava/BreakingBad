package com.example.breakingbad.ui.navHome

import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.example.breakingbad.R
import com.example.breakingbad.databinding.FragmentNavHomeBinding
import com.example.breakingbad.ui.BaseFragment

class NavHomeFragment : BaseFragment<FragmentNavHomeBinding>(FragmentNavHomeBinding::inflate) {
    override fun start() {

        setListeners()
    }


    private fun setListeners() {


        binding.homeNavTabBar.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.homeScreenMenu -> {
                    binding.homeNavContainer.findNavController().navigate(R.id.toHomeFragment)
                    true
                }

                R.id.savedScreenMenu -> {
                    binding.homeNavContainer.findNavController().navigate(R.id.toSavedFragment)
                    true
                }
                R.id.profileScreenMenu -> {
                    binding.homeNavContainer.findNavController().navigate(R.id.toProfileFragment)
                    true
                }

                R.id.searchScreenMenu -> {
                    binding.homeNavContainer.findNavController().navigate(R.id.toSearchFragment)
                    true
                }
                else -> false
            }
        }


    }


}