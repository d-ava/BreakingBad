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
import com.example.breakingbad.R
import com.example.breakingbad.api.BBEpisodes
import com.example.breakingbad.api.NetworkClient
import com.example.breakingbad.databinding.FragmentSeasonBinding
import com.example.breakingbad.ui.BaseFragment
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


class SeasonFragment : BaseFragment<FragmentSeasonBinding>(FragmentSeasonBinding::inflate) {
    private var seriesList = listOf<com.example.breakingbad.model.BBEpisodes>()
    private val args: SeasonFragmentArgs by navArgs()

    override fun start() {
        setListeners()
        binding.tvSeason.text = args.series
        getEpisodes()
    }

    private fun getEpisodes() {
        val list2 = mutableListOf<com.example.breakingbad.model.BBEpisodes>()
        val list3 = mutableListOf<com.example.breakingbad.model.BBEpisodes>()
        val series = args.series
        lifecycleScope.launchWhenStarted {
            withContext(Dispatchers.IO) {
                val response = NetworkClient.bbEpisodesApi.getEpisodes()
                val body = response.body()
                if (response.isSuccessful && body != null) {
                    val list4 = body.filter { series[1] in it.series && series[0] in it.season }
                    Log.d("---", "filtered - $list4")
//                    for (i in body) {
//                        if (series[1] in i.series){
//                            list2.add(i)
//                        }
//                    }
//                    for (i in list2){
//                        if (series[0] in i.season){
//                            list3.add(i)
//                        }
//                    }
//
//                    Log.d("---", "list 3 = $list3")

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