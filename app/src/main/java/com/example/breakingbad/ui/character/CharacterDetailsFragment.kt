package com.example.breakingbad.ui.character

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
import com.example.breakingbad.databinding.FragmentCharacterDetailsBinding
import com.example.breakingbad.extensions.makeSnackbar
import com.example.breakingbad.ui.BaseFragment
import com.example.breakingbad.util.Resource
import com.example.breakingbad.util.Utils
import com.example.breakingbad.util.Utils.convertStringToListOfInt
import com.example.breakingbad.util.Utils.savedCharacterslist
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

var intList: MutableList<Int> = mutableListOf()

@AndroidEntryPoint
class CharacterDetailsFragment :
    BaseFragment<FragmentCharacterDetailsBinding>(FragmentCharacterDetailsBinding::inflate) {


    private val args: CharacterDetailsFragmentArgs by navArgs()
    private val appearanceList = mutableListOf<String>()
    private lateinit var seriesAdapter: SeriesAdapter
    private val viewModel: CharacterDetailsViewModel by viewModels()

    //to add/remove character to savedCharactersList
    private val savedCharactersListInt = mutableListOf<Int>()
    val newCharactersList = mutableListOf<Int>()


    override fun start() {

        intList = convertStringToListOfInt(savedCharacterslist)
        setCharacterInformation()
        setRecycler()


        getQuotes()
        loadSavedCharacters()

        setListeners()
//        saveRemoveButton()
        Log.d("---", "saved characters list chdetail-> ${Utils.savedCharacterslist}")

    }

    //load to change button textt and action
    @SuppressLint("UnsafeRepeatOnLifecycleDetector")
    private fun loadSavedCharacters() {
        viewModel.loadSavedCharacters()

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.loadSavedCharactersList.collect {
//                    Log.d("---", "characterId -> ${it.characterId}")
                    if (convertStringToListOfInt(it.characterId).contains(args.bbCharacterInformation.charId)){
                        binding.btnAddRemove.text = "Remove001"
                    }else{
                        binding.btnAddRemove.text = "Add001"
                        binding.btnAddRemove.setOnClickListener {

                            savedCharacterslist += ",${args.bbCharacterInformation.charId.toString()}"
                            saveRemoveCharacter(savedCharacterslist)

                        }
                    }

                }
            }
        }

    }

    private fun saveRemoveButton() {

        if (intList.contains(args.bbCharacterInformation.charId)) {


            binding.btnAddRemove.text = "Remove"

        } else {
            binding.btnAddRemove.text = "Add"
            binding.btnAddRemove.setOnClickListener {

                savedCharacterslist += ",${args.bbCharacterInformation.charId.toString()}"
                saveRemoveCharacter(savedCharacterslist)

            }

        }

    }


    @SuppressLint("UnsafeRepeatOnLifecycleDetector")
    private fun saveRemoveCharacter(newList: String) {


        viewModel.saveCharacterId(newList)
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.saveCharacter.collect {
                    when (it) {
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