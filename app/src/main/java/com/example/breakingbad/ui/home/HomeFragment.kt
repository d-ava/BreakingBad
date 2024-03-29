package com.example.breakingbad.ui.home

import android.annotation.SuppressLint
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
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
import com.example.breakingbad.util.Utils
import com.example.breakingbad.util.Utils.auth
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext



@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate) {

    private lateinit var bbadapter: BBAdapter

    private lateinit var characterPagingAdapter: BBPagingAdapter
    private val sharedViewModel: CharactersViewModel by activityViewModels()

    override fun start() {

        if (auth.currentUser != null) {
            loadSavedCharacters()
        }

        setRecycler()
        checkAndLoadCharacters()
        getCharacters()



//        setRecyclerPager()
//        getCharactersUsingPager()


    }

    private fun setRecyclerPager() {
        characterPagingAdapter = BBPagingAdapter {
            val action = CharacterDetailsFragmentDirections.toCharacterDetailsFragment(it)
            activity?.findNavController(R.id.mainContainer)?.navigate(action)
        }
        binding.apply {
            recycler.layoutManager = GridLayoutManager(requireContext(), 2)
            recycler.adapter = characterPagingAdapter.withLoadStateFooter(
                footer = LoadingStateAdapter()
            )
        }
    }

    private fun getCharactersUsingPager() {
        viewLifecycleOwner.lifecycleScope.launch {
            sharedViewModel.loadCharactersUsingPaging().collectLatest {
                characterPagingAdapter.submitData(it)
            }
        }


    }


    @SuppressLint("UnsafeRepeatOnLifecycleDetector")
    private fun loadSavedCharacters() {
        sharedViewModel.loadSavedCharacters()

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                sharedViewModel.loadSavedCharactersList.collect {
                    Log.d("---", "user info -> $it")
                    Utils.authUserInfo = it
                }
            }
        }

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
                        else -> {
                            Unit
                        }
                    }
                }
            }
        }
    }

    private fun getCharactersFromRoom() {
        sharedViewModel.loadCharactersFromRoom()
        lifecycleScope.launchWhenStarted {
            sharedViewModel.loadCharactersFromRoom.collect {
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
                    else -> {
                        Unit
                    }
                }

            }

        }
    }


    private fun setRecycler() {
        bbadapter = BBAdapter {
            val action = CharacterDetailsFragmentDirections.toCharacterDetailsFragment(it)
            activity?.findNavController(R.id.mainContainer)?.navigate(action)
        }
        binding.recycler.apply {
            adapter = bbadapter
            layoutManager = GridLayoutManager(requireContext(), 2)

        }
    }

    private fun checkAndLoadCharacters() {
        if (checkForInternet(requireContext())) {
//            view?.makeSnackbar("loading from internet")
            getCharacters()
        } else {
//            view?.makeSnackbar("no internet, loading cashed data")
            getCharactersFromRoom()

        }
    }

    private fun checkForInternet(context: Context): Boolean {

        // register activity with the connectivity manager service
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        // if the android version is equal to M
        // or greater we need to use the
        // NetworkCapabilities to check what type of
        // network has the internet connection
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            // Returns a Network object corresponding to
            // the currently active default data network.
            val network = connectivityManager.activeNetwork ?: return false

            // Representation of the capabilities of an active network.
            val activeNetwork = connectivityManager.getNetworkCapabilities(network) ?: return false

            return when {
                // Indicates this network uses a Wi-Fi transport,
                // or WiFi has network connectivity
                activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true

                // Indicates this network uses a Cellular transport. or
                // Cellular has network connectivity
                activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true

                // else return false
                else -> false
            }
        } else {
            // if the android version is below M
            @Suppress("DEPRECATION") val networkInfo =
                connectivityManager.activeNetworkInfo ?: return false
            @Suppress("DEPRECATION")
            return networkInfo.isConnected
        }
    }


}