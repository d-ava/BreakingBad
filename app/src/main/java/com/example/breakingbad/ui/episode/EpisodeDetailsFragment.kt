package com.example.breakingbad.ui.episode

import android.annotation.SuppressLint
import android.util.Log
import androidx.core.view.setPadding
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.breakingbad.R
import com.example.breakingbad.databinding.FragmentEpisodeDetailsBinding
import com.example.breakingbad.db.BBDao
import com.example.breakingbad.db.BBDatabase
import com.example.breakingbad.extensions.makeSnackbar
import com.example.breakingbad.model.BBCharacter
import com.example.breakingbad.ui.BaseFragment
import com.example.breakingbad.ui.character.CharacterDetailsFragmentDirections
import com.example.breakingbad.ui.viewModel.CharactersViewModel
import com.example.breakingbad.util.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class EpisodeDetailsFragment :
    BaseFragment<FragmentEpisodeDetailsBinding>(FragmentEpisodeDetailsBinding::inflate) {

    //    private val viewModel: EpisodeDetailsViewModel by viewModels()
    private lateinit var episodesAdapter: EpisodeDetailsAdapter02
    private val args: EpisodeDetailsFragmentArgs by navArgs()
    private val sharedViewModel: CharactersViewModel by activityViewModels()


    override fun start() {


        setEpisodeInformation()
//
        setListeners()
//
        setRecycler()


        getCharactersByName()


    }

    @SuppressLint("UnsafeRepeatOnLifecycleDetector")
    private fun getCharactersByName() {
        val finalList = mutableListOf<BBCharacter>()
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                sharedViewModel.loadCharacters.collect {
                    when (it) {
                        is Resource.Loading -> {
                            showLoading()
                        }
                        is Resource.Success -> {
//                            Log.d("---", "success -> ${it.data}")
                            hideLoading()
                            for (char in args.episodeDetails.characters) {
                                for (char2 in it.data!!) {
                                    if (char == char2.name) {
                                        finalList.add(char2)
                                    }
                                }
                            }
                            episodesAdapter.setData(finalList)

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


    private fun setEpisodeInformation() {
        binding.tvEpisodeName.text = args.episodeDetails.title
        binding.tvSeasonNumber.text = "Season" + args.episodeDetails.season //todo

        if ("S" in args.episodeDetails.series) {
            binding.ivEpisodeLogo.setBackgroundResource(R.drawable.ic_better_cal_saul_logo)
        }
    }


    private fun setRecycler() {
        episodesAdapter = EpisodeDetailsAdapter02 {
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