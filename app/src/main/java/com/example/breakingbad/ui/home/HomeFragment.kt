package com.example.breakingbad.ui.home

import android.util.Log
import androidx.lifecycle.lifecycleScope
import com.example.breakingbad.api.NetworkClient
import com.example.breakingbad.databinding.FragmentHomeBinding
import com.example.breakingbad.ui.BaseFragment
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


class HomeFragment : BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate) {

    override fun start() {

      getBBCharacters()



    }

    private fun getBBCharacters(){
        lifecycleScope.launchWhenStarted {
            withContext(Dispatchers.IO){
                val response = NetworkClient.bbCharactersApi.getBBCharacters()
                val body = response.body()
                if (response.isSuccessful && body !=null){
                    Log.d("---", "$body")
                }
                else{
                Log.d("---", "${response.code()}")
                }
            }
        }
    }

}