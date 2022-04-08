package com.example.breakingbad.ui.character

import android.util.Log
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.breakingbad.R
import com.example.breakingbad.databinding.FragmentCharacterDetailsBinding
import com.example.breakingbad.extensions.makeSnackbar
import com.example.breakingbad.model.User
import com.example.breakingbad.ui.BaseFragment
import com.example.breakingbad.util.Utils.auth
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.squareup.picasso.Picasso
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.withContext


class CharacterDetailsFragment :
    BaseFragment<FragmentCharacterDetailsBinding>(FragmentCharacterDetailsBinding::inflate) {

    //    lateinit var auth: FirebaseAuth
//    private lateinit var database: DatabaseReference

//    private val args: CharacterDetailsFragmentArgs by navArgs()
//    private val appearanceList = mutableListOf<String>()
//    private lateinit var seriesAdapter: SeriesAdapter
//    private val viewModel: CharacterDetailsViewModel by viewModels()
//
//    val charList: MutableList<String> = mutableListOf()

    override fun start() {
//        Log.d("---", "uid - ${auth.uid}")
//        auth = Firebase.auth
//        database = Firebase.database.reference

//        database = FirebaseDatabase.getInstance().getReference("users")


//        setListeners()
//        onBackPressed()
//
//        setCharacterInformation()
//        setRecycler()


//        getQuotes() //
    }




//    private fun setCharacterInformation() {
//        val character = args.bbCharacterInformation
//
//        var quotes = ""
//        var occupations = ""
//        for (occupation in character.occupation) {
//            occupations = occupations + occupation + "\n"
//        }
//
//
//        Picasso.get().load(character.img).into(binding.ivCharacter)
//        binding.apply {
//            tvName.text = character.name
//            tvBirthday.text = character.birthday
//            tvNickname.text = character.nickname.uppercase()
//            tvPortrayed.text = character.portrayed
//            tvStatus.text = character.status
//            tvOccupation.text = occupations
//        }
//
//        //series
//
//        for (series in character.appearance) {
//            appearanceList.add(series.toString() + "d") //"d" for breaking ba"D"
//        }
//        for (series in character.betterCallSaulAppearance) {
//            appearanceList.add(series.toString() + "S") //"S" for better call "S"aul
//        }
//
//        Log.d("---", "$appearanceList")
//
//        //quotes
////        for (quote in bbQuotes) {
////            if (quote.author.lowercase() == character.name.lowercase()) {
////                quotes = quotes + quote.quote + "\n\n"
////            }
////        }
////        binding.tvQuotes.text = quotes
//
//
//        viewModel.getQuotesFromAuthor(character.name)
//        lifecycleScope.launchWhenStarted {
//            withContext(Dispatchers.IO) {
//                viewModel.getQuotes.collect {
//
////                    Log.d("---", "get quotes from Room = $it")
//                    for (i in it) {
//                        quotes = quotes + i.quote + "\n\n"
//                    }
//
//                    withContext(Dispatchers.Main) {
////                        binding.tvQuotes.text = it.joinToString(separator = "\n\n")
//                        binding.tvQuotes.text = quotes
//
//                    }
//                }
//            }
//        }
//
//    }


//    private fun setRecycler() {
//        seriesAdapter = SeriesAdapter {
////            view?.makeSnackbar(it)
//            val action = CharacterDetailsFragmentDirections.toSeasonFragment(it)
//            activity?.findNavController(R.id.mainContainer)?.navigate(action)
//        }
//        binding.recyclerViewSeries.adapter = seriesAdapter
//        binding.recyclerViewSeries.layoutManager =
//            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
//        seriesAdapter.setData(appearanceList)
//    }
//
//    private fun setListeners() {
//        binding.backArrow.setOnClickListener {
//
//
//            findNavController().popBackStack()
//        }
//
//        binding.btnAddRemove.setOnClickListener {
//
//
//        }
//    }

//    private fun onBackPressed() {
//        activity?.onBackPressedDispatcher?.addCallback(this, object : OnBackPressedCallback(true) {
//            override fun handleOnBackPressed() {
//                findNavController().popBackStack()
//
//            }
//        })
//    }
}