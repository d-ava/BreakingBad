package com.example.breakingbad.ui.profile

import androidx.navigation.findNavController
import com.example.breakingbad.R
import com.example.breakingbad.databinding.FragmentProfileBinding
import com.example.breakingbad.ui.BaseFragment
import com.example.breakingbad.util.Utils.auth
import com.example.breakingbad.util.Utils.authUserInfo
import com.example.breakingbad.util.lang


class ProfileFragment : BaseFragment<FragmentProfileBinding>(FragmentProfileBinding::inflate) {

    override fun start() {
        setListeners()
        setUserInfo()

        changeLanguage()


    }

    private fun changeLanguage() {
        if (lang == "") {
            lang = "ka"
            binding.tvLanguage.setOnClickListener {
                activity?.recreate()
            }
        } else if (lang == "ka") {
            lang = ""
            binding.tvLanguage.setOnClickListener {
                activity?.recreate()
            }
        }


    }

    private fun setUserInfo() {
        if (auth.currentUser != null) {
            binding.apply {
                tvEmail.text = authUserInfo.email
                tvName.text = authUserInfo.name
            }
        }

    }


    private fun setListeners() {
        if (auth.currentUser != null) {
            binding.apply {
                tvLoginLogout.text = getString(R.string.login)
                tvLoginLogout.setOnClickListener {
                    auth.signOut()
                    activity?.recreate()
//                    activity?.findNavController(R.id.mainContainer)?.navigate(R.id.toLoginFragment)
                }
            }
        } else {
            binding.apply {
                tvLoginLogout.setOnClickListener {

                    activity?.findNavController(R.id.mainContainer)?.navigate(R.id.toLoginFragment)
                }
            }

        }

    }
}