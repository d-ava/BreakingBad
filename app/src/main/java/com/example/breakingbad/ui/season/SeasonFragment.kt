package com.example.breakingbad.ui.season

import android.annotation.SuppressLint
import android.util.Log
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.breakingbad.R
import com.example.breakingbad.api.BBEpisodesApi
import com.example.breakingbad.api.NetworkClient
import com.example.breakingbad.databinding.FragmentSeasonBinding
import com.example.breakingbad.extensions.makeSnackbar
import com.example.breakingbad.ui.BaseFragment
import com.example.breakingbad.util.Resource
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@AndroidEntryPoint
class SeasonFragment : BaseFragment<FragmentSeasonBinding>(FragmentSeasonBinding::inflate) {

    private val args: SeasonFragmentArgs by navArgs()
    private lateinit var episodesAdapter: EpisodesAdapter
    private val viewModel:SeasonViewModel by viewModels()


    override fun start() {

        getEpisodes()
        setRecycler()
        setTitle()
        setListeners()
    }

    private fun setTitle() {
        val series = args.series
        binding.tvSeason.text = getString(R.string.season) + " ${series[0]}"  //need to solve this. but not now
    }

    private fun setRecycler() {
        episodesAdapter = EpisodesAdapter {
            val action = SeasonFragmentDirections.toEpisodeDetailsFragment(it)

            findNavController().navigate(action)
        }
        binding.recycler.adapter = episodesAdapter
        binding.recycler.layoutManager = LinearLayoutManager(requireContext())


    }

    @SuppressLint("UnsafeRepeatOnLifecycleDetector")
    private fun getEpisodes(){
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED){
                viewModel.getEpisodes.collect { it ->
                    when(it){
                        is Resource.Loading -> {
                            showLoading()
                        }
                        is Resource.Success -> {
                            hideLoading()
                            episodesAdapter.setData(it.data!!.filter { args.series[1] in it.series && args.series[0] in it.season })
                            Log.d("---", "episodes info ${it.data}")
                        }
                        is Resource.Error -> {
                            hideLoading()
                            view?.makeSnackbar("${it.message}")
                        }
                        else -> Unit
                    }
                }
            }
        }

    }


    private fun setListeners() {
        binding.apply {
            backArrow.setOnClickListener {
                findNavController().popBackStack()
            }

            goHome.setOnClickListener {
                activity?.findNavController(R.id.mainContainer)?.navigate(R.id.navHomeFragment)
            }
        }


    }
}