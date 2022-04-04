package com.example.breakingbad.ui.episode

import android.util.Log
import androidx.core.view.setPadding
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.breakingbad.R
import com.example.breakingbad.databinding.FragmentEpisodeDetailsBinding
import com.example.breakingbad.db.BBDao
import com.example.breakingbad.db.BBDatabase
import com.example.breakingbad.model.BBCharacter
import com.example.breakingbad.ui.BaseFragment
import com.example.breakingbad.ui.character.CharacterDetailsFragmentDirections
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.withContext


class EpisodeDetailsFragment :
    BaseFragment<FragmentEpisodeDetailsBinding>(FragmentEpisodeDetailsBinding::inflate) {

    private val viewModel: EpisodeDetailsViewModel by viewModels()
    private lateinit var episodesAdapter: EpisodeDetailsAdapter02
    private val args: EpisodeDetailsFragmentArgs by navArgs()



    override fun start() {

//        Log.d("---", "args on episode details $args")


        setEpisodeInformation()

        setListeners()

        setRecycler()



        getCharacterByName()

    }

    private fun getCharacterByName() {
        val episodeCharacters = args.episodeDetails.characters
        val finalList = mutableListOf<BBCharacter>()
        lifecycleScope.launchWhenStarted {
            withContext(Dispatchers.IO) {
                viewModel.loadCharacters.collect { allCharacters ->
//                    Log.d("---", "all - $allCharacters")
                    Log.d("---", "character - $episodeCharacters")

                    for (char in episodeCharacters) {
                        for (char2 in allCharacters) {
                            if (char == char2.name) {
                                finalList.add(char2)
                            }
                        }
                    }

                    withContext(Dispatchers.Main){
                      episodesAdapter.setData(finalList)
                    }
                    Log.d("---", "final list - $finalList")
                }
            }
        }


    }

    private fun setEpisodeInformation() {
        binding.tvEpisodeName.text = args.episodeDetails.title
        binding.tvSeasonNumber.text = "Season" + args.episodeDetails.season //todo

        if ("S" in args.episodeDetails.series) {
            binding.ivEpisodeLogo.setBackgroundResource(R.drawable.ic_better_cal_saul_logo)
        }
    }


    private fun setRecycler() {
        episodesAdapter = EpisodeDetailsAdapter02{
            val action = CharacterDetailsFragmentDirections.toCharacterDetailsFragment(it)
            findNavController().navigate(action)
        }
        binding.recycler.adapter = episodesAdapter
        binding.recycler.layoutManager = LinearLayoutManager(requireContext())


    }

    private fun setListeners() {
        binding.backArrow.setOnClickListener {
            findNavController().popBackStack()
        }
    }
}