package com.example.breakingbad.ui.episode

import android.util.Log
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.breakingbad.databinding.FragmentEpisodeDetailsBinding
import com.example.breakingbad.ui.BaseFragment
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


class EpisodeDetailsFragment : BaseFragment<FragmentEpisodeDetailsBinding>(FragmentEpisodeDetailsBinding::inflate) {

    private val viewModel:EpisodeDetailsViewModel by viewModels()
    private lateinit var episodesAdapter: EpisodeDetailsAdapter
    private val args:EpisodeDetailsFragmentArgs by navArgs()


    override fun start() {

        Log.d("---", "args on episode details $args")
        binding.tvEpisodeName.text = args.episodeDetails.title
        binding

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