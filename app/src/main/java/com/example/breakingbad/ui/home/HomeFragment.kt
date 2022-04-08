package com.example.breakingbad.ui.home

import android.annotation.SuppressLint
import android.util.Log
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.breakingbad.R
import com.example.breakingbad.api.NetworkClient
import com.example.breakingbad.databinding.FragmentHomeBinding
import com.example.breakingbad.extensions.makeSnackbar
import com.example.breakingbad.model.BBQuotes
import com.example.breakingbad.ui.BaseFragment
import com.example.breakingbad.ui.character.CharacterDetailsFragmentDirections
import com.example.breakingbad.ui.viewModel.CharactersViewModel
import com.example.breakingbad.util.Resource
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

//var bbQuotes: List<BBQuotes> = listOf()

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate) {

    private lateinit var bbadapter: BBAdapter

    //    private val viewModel: HomeViewModel by activityViewModels()
    private val sharedViewModel: CharactersViewModel by activityViewModels()

    override fun start() {

        setRecycler()
//        getBBCharacters()
//        getBBCharactersFromRoom()
//        getQuotes()
//        getQuotesFromRoom()

        getCharacters()

    }

    @SuppressLint("UnsafeRepeatOnLifecycleDetector")
    private fun getCharacters() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                sharedViewModel.loadCharacters.collect {
                    when (it) {
                        is Resource.Loading -> {
                            showLoading()
//                            Log.d ("---", "getCharacters loading")
                        }
                        is Resource.Success -> {
                            hideLoading()
                            bbadapter.setData(it.data!!)
//                            Log.d ("---", "getCharacters ${it.data}")
                        }
                        is Resource.Error -> {
                            hideLoading()
                            view?.makeSnackbar(it.message!!)
                        }
                        else -> {Unit}
                    }
                }
            }
        }
    }

//    private fun getBBCharactersFromRoom() {
//        lifecycleScope.launchWhenStarted {
//            viewModel.loadCharacters.collect {
//                bbadapter.setData(it)
//            }
//        }
//    }

//    private fun getQuotes() {
//        lifecycleScope.launchWhenStarted {
//            withContext(Dispatchers.IO) {
//                val response = NetworkClient.bbQuotesApi.getQuotes()
//                val body = response.body()
//                if (response.isSuccessful && body != null) {
//                    Log.d("---", "$body")
//                    bbQuotes = body
//                }
//            }
//        }
//    }

//    private fun getQuotesFromRoom() {
//        lifecycleScope.launchWhenStarted {
//            viewModel.loadQuotes.collect {
//                bbQuotes=it
//
//            }
//        }
//
//    }

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

//    private fun getBBCharacters() {
//        lifecycleScope.launchWhenStarted {
//            withContext(Dispatchers.IO) {
//                val response = NetworkClient.bbCharactersApi.getBBCharacters()
//                val body = response.body()
//                if (response.isSuccessful && body != null) {
//                    Log.d("---", "$body")
//                    withContext(Dispatchers.Main) {
//                        bbadapter.setData(body)
//                    }
//                } else {
//                    Log.d("---", "${response.code()}")
//                }
//            }
//        }
//    }


}