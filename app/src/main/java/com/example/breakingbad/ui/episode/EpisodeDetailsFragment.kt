package com.example.breakingbad.ui.episode

import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.breakingbad.databinding.FragmentEpisodeDetailsBinding
import com.example.breakingbad.ui.BaseFragment
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


class EpisodeDetailsFragment : BaseFragment<FragmentEpisodeDetailsBinding>(FragmentEpisodeDetailsBinding::inflate) {

    private val viewModel:EpisodeDetailsViewModel by viewModels()
    private lateinit var episodesAdapter: EpisodeDetailsAdapter


    override fun start() {

        setListeners()

        setRecycler()

        getCharacters()


    }

    private fun getCharacters(){
        lifecycleScope.launchWhenStarted {
            withContext(Dispatchers.IO){

            }
        }

    }

    private fun setRecycler(){
        episodesAdapter = EpisodeDetailsAdapter()
        binding.recycler.adapter = episodesAdapter
        binding.recycler.layoutManager = LinearLayoutManager(requireContext())

    }

    private fun setListeners(){
        binding.backArrow.setOnClickListener {
            findNavController().popBackStack()
        }
    }
}