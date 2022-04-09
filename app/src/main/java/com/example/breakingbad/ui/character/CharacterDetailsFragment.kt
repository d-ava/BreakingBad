package com.example.breakingbad.ui.character

import android.annotation.SuppressLint
import android.util.Log
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.breakingbad.R
import com.example.breakingbad.databinding.FragmentCharacterDetailsBinding
import com.example.breakingbad.extensions.makeSnackbar
import com.example.breakingbad.ui.BaseFragment
import com.example.breakingbad.util.Resource
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CharacterDetailsFragment :
    BaseFragment<FragmentCharacterDetailsBinding>(FragmentCharacterDetailsBinding::inflate) {


    private val args: CharacterDetailsFragmentArgs by navArgs()
    private val appearanceList = mutableListOf<String>()
    private lateinit var seriesAdapter: SeriesAdapter
    private val viewModel: CharacterDetailsViewModel by viewModels()
//
//    val charList: MutableList<String> = mutableListOf()

    override fun start() {


//        onBackPressed()
        setCharacterInformation()
        setRecycler()


        getQuotes()
//        getQuotesLiveData()
        setListeners()

    }

    @SuppressLint("UnsafeRepeatOnLifecycleDetector")
    private fun saveCharacter(){
        val characterId=args.bbCharacterInformation.charId
        viewModel.saveCharacterId(characterId)
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED){
                viewModel.saveCharacter.collect {
                    when(it){
                        is Resource.Loading -> {
                            showLoading()
                        }
                        is Resource.Success -> {
                            hideLoading()
                            view?.makeSnackbar("character saved")
                        }
                        is Resource.Error -> {
                            hideLoading()
                            view?.makeSnackbar("${it.message}")
                        }
                    }
                }
            }
        }
    }

//    private fun getQuotesLiveData() {
//        viewModel.fetchQuotes.observe(this, Observer {
//            when (it) {
//                is Resource.Loading -> {
//                    showLoading()
//                }
//                is Resource.Success -> {
//                    hideLoading()
//                    var quotes = ""
//                    for (quote in it.data!!) {
//                        if (quote.author.lowercase() == args.bbCharacterInformation.name.lowercase()) {
//                            quotes = quotes + quote.quote + "\n\n"
//                        }
//                    }
//                    binding.tvQuotes.text = quotes
//
////                            bbQuotes=it.data!!
//                    Log.d("---", "get quotes ${it.data}")
//                }
//                is Resource.Error -> {
//                    hideLoading()
//                    view?.makeSnackbar(it.message!!)
//                }
//                else -> Unit
//            }
//        })
//    }

    @SuppressLint("UnsafeRepeatOnLifecycleDetector")
    private fun getQuotes() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.loadQuotes.collect {
                    when (it) {
                        is Resource.Loading -> {
                            showLoading()
                        }
                        is Resource.Success -> {
                            hideLoading()
                            var quotes = ""
                            for (quote in it.data!!) {
                                if (quote.author.lowercase() == args.bbCharacterInformation.name.lowercase()) {
                                    quotes = quotes + quote.quote + "\n\n"
                                }
                            }
                            binding.tvQuotes.text = quotes

//                            bbQuotes=it.data!!
                            Log.d("---", "get quotes ${it.data}")
                        }
                        is Resource.Error -> {
                            hideLoading()
                            view?.makeSnackbar(it.message!!)
                        }
                        else -> Unit
                    }
                }
            }
        }
    }


    private fun setCharacterInformation() {
        val character = args.bbCharacterInformation

//        var quotes = ""
        var occupations = ""
        for (occupation in character.occupation) {
            occupations = occupations + occupation + "\n"
        }


        Picasso.get().load(character.img).into(binding.ivCharacter)
        binding.apply {
            tvName.text = character.name
            tvBirthday.text = character.birthday
            tvNickname.text = character.nickname.uppercase()
            tvPortrayed.text = character.portrayed
            tvStatus.text = character.status
            tvOccupation.text = occupations
        }

        //series

        for (series in character.appearance) {
            appearanceList.add(series.toString() + "d") //"d" for breaking ba"D"
        }
        for (series in character.betterCallSaulAppearance) {
            appearanceList.add(series.toString() + "S") //"S" for better call "S"aul
        }


    }


    private fun setRecycler() {
        seriesAdapter = SeriesAdapter {

            val action = CharacterDetailsFragmentDirections.toSeasonFragment(it)
            activity?.findNavController(R.id.mainContainer)?.navigate(action)
        }
        binding.recyclerViewSeries.adapter = seriesAdapter
        binding.recyclerViewSeries.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        seriesAdapter.setData(appearanceList)
    }

    private fun setListeners() {
        binding.backArrow.setOnClickListener {


            findNavController().popBackStack()
        }

        binding.btnAddRemove.setOnClickListener {
            saveCharacter()


        }
    }

//    private fun onBackPressed() {
//        activity?.onBackPressedDispatcher?.addCallback(this, object : OnBackPressedCallback(true) {
//            override fun handleOnBackPressed() {
//                findNavController().popBackStack()
//
//            }
//        })
//    }
}