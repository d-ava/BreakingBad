package com.example.breakingbad.ui.home

import android.util.Log
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.breakingbad.R
import com.example.breakingbad.api.NetworkClient
import com.example.breakingbad.databinding.FragmentHomeBinding
import com.example.breakingbad.model.BBQuotes
import com.example.breakingbad.ui.BaseFragment
import com.example.breakingbad.ui.character.CharacterDetailsFragmentDirections
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

var bbQuotes: List<BBQuotes> = listOf()

class HomeFragment : BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate) {

    private lateinit var bbadapter: BBAdapter


    override fun start() {

        setRecycler()
        getBBCharacters()
        getQuotes()

    }

    private fun getQuotes() {
        lifecycleScope.launchWhenStarted {
            withContext(Dispatchers.IO) {
                val response = NetworkClient.bbQuotesApi.getQuotes()
                val body = response.body()
                if (response.isSuccessful && body != null) {
                    Log.d("---", "$body")
                    bbQuotes = body
                }
            }
        }
    }

    private fun setRecycler() {
        bbadapter = BBAdapter {
            val action = CharacterDetailsFragmentDirections.toCharacterDetailsFragment(it)
            activity?.findNavController(R.id.mainContainer)?.navigate(action)
//            view?.makeSnackbar("name is ${it.nickname}")
//            showDialogMain(R.string.app_name, R.string.error)
        }
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
                    withContext(Dispatchers.Main) {
                        bbadapter.setData(body)
                    }
                } else {
                    Log.d("---", "${response.code()}")
                }
            }
        }
    }


}