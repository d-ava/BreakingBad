package com.example.breakingbad.ui.home

import android.util.Log
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.breakingbad.api.NetworkClient
import com.example.breakingbad.databinding.FragmentHomeBinding
import com.example.breakingbad.extensions.makeSnackbar
import com.example.breakingbad.model.BBCharacter
import com.example.breakingbad.ui.BaseFragment
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

var bbList = listOf<BBCharacter>()

class HomeFragment : BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate) {

    private lateinit var bbadapter: BBAdapter


    override fun start() {

        setRecycler()
        getBBCharacters()

        bbadapter.setData(bbList)
    }

    private fun setRecycler() {
        bbadapter = BBAdapter()
        binding.recycler.apply {
            adapter = bbadapter
            layoutManager = GridLayoutManager(requireContext(), 2)
        }
    }

    private fun getBBCharacters() {
        lifecycleScope.launchWhenStarted {
            withContext(Dispatchers.IO) {
                val response = NetworkClient.bbCharactersApi.getBBCharacters()
                val body = response.body()
                if (response.isSuccessful && body != null) {
                    Log.d("---", "$body")
                    bbList = body
                } else {
                    Log.d("---", "${response.code()}")
                }
            }
        }
    }


}