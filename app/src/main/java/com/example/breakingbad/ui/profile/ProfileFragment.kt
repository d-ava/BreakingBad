package com.example.breakingbad.ui.profile

import androidx.navigation.findNavController
import com.example.breakingbad.R
import com.example.breakingbad.databinding.FragmentProfileBinding
import com.example.breakingbad.ui.BaseFragment
import com.example.breakingbad.util.Utils.auth
import com.example.breakingbad.util.Utils.authUserInfo


class ProfileFragment : BaseFragment<FragmentProfileBinding>(FragmentProfileBinding::inflate) {

    override fun start() {
        setListeners()
        setUserInfo()


    }

    private fun setUserInfo(){
        if(auth.currentUser !=null){
            binding.apply {
                tvEmail.text = authUserInfo.email
                tvName.text = authUserInfo.name
            }
        }

    }



    private fun setListeners(){
        if (auth.currentUser != null){
            binding.apply {
                tvLoginLogout.text = "Logout"
                tvLoginLogout.setOnClickListener {
                    auth.signOut()
                    activity?.findNavController(R.id.mainContainer)?.navigate(R.id.toLoginFragment)
                }
            }

        }
        binding.tvLoginLogout.setOnClickListener {
            activity?.findNavController(R.id.mainContainer)?.navigate(R.id.toLoginFragment)
        }
    }
}