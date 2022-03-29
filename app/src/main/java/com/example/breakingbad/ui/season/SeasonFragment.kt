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
        val ser = args.series
        lifecycleScope.launchWhenStarted {
            withContext(Dispatchers.IO) {
                val response = NetworkClient.bbEpisodesApi.getEpisodes()
                val body = response.body()
                if (response.isSuccessful && body != null) {
                    Log.d("---", "all series $body")
                    if ("b" in ser){
                        for (series in body){
                            Unit
//                            if (ser[0] in series.season){
//                                seriesList.add(series)
//                            }
                        }
                    }else if ("s" in ser){
                       seriesList = body.filter {ser[0] in it.series}
                        Log.d("---", "series in $seriesList")
                        Log.d("---", "${ser[0]}")
                    }
                }
            }
        }

        Log.d("---", "series $seriesList")
    }

    private fun setListeners() {
        binding.backArrow.setOnClickListener {
            findNavController().popBackStack()
        }
    }
}