package com.example.breakingbad.ui.season

import android.net.Network
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.breakingbad.R
import com.example.breakingbad.api.BBEpisodes
import com.example.breakingbad.api.NetworkClient
import com.example.breakingbad.databinding.FragmentSeasonBinding
import com.example.breakingbad.ui.BaseFragment
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


class SeasonFragment : BaseFragment<FragmentSeasonBinding>(FragmentSeasonBinding::inflate) {

    private val args: SeasonFragmentArgs by navArgs()
    private lateinit var episodesAdapter: EpisodesAdapter


    override fun start() {
        setListeners()

        getEpisodes()
        setRecycler()
        setTitle()
    }

    private fun setTitle() {
        val series = args.series
        binding.tvSeason.text = "SEASON ${series[0]}"  //need to solve this. but not now
    }

    private fun setRecycler() {
        episodesAdapter = EpisodesAdapter {
            findNavController().navigate(SeasonFragmentDirections.toEpisodeDetailsFragment())
        }
        binding.recycler.adapter = episodesAdapter
        binding.recycler.layoutManager = LinearLayoutManager(requireContext())


    }

    private fun getEpisodes() {

        val series = args.series
        lifecycleScope.launchWhenStarted {
            withContext(Dispatchers.IO) {
                val response = NetworkClient.bbEpisodesApi.getEpisodes()
                val body = response.body()
                if (response.isSuccessful && body != null) {
                    withContext(Dispatchers.Main) {
                        episodesAdapter.setData(body.filter { series[1] in it.series && series[0] in it.season })
                    }


                }
            }
        }


    }

    private fun setListeners() {
        binding.backArrow.setOnClickListener {
            findNavController().popBackStack()
        }
    }
}