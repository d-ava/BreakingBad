package com.example.breakingbad.ui.profile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import com.example.breakingbad.R
import com.example.breakingbad.databinding.FragmentProfileBinding
import com.example.breakingbad.ui.BaseFragment
import com.example.breakingbad.util.Utils.authUserInfo


class ProfileFragment : BaseFragment<FragmentProfileBinding>(FragmentProfileBinding::inflate) {

    override fun start() {
        setListeners()
        setUserInfo()
    }

    private fun setUserInfo(){
        binding.apply {
            tvEmail.text = authUserInfo.email
            tvName.text = authUserInfo.name
        }
    }


    private fun setListeners(){
        binding.tvLoginLogout.setOnClickListener {
            activity?.findNavController(R.id.mainContainer)?.navigate(R.id.toLoginFragment)
        }
    }
}