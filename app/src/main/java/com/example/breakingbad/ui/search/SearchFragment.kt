package com.example.breakingbad.ui.search

import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.breakingbad.R
import com.example.breakingbad.databinding.FragmentSearchBinding
import com.example.breakingbad.model.BBCharacter
import com.example.breakingbad.ui.BaseFragment
import com.example.breakingbad.ui.character.CharacterDetailsFragmentDirections
import com.example.breakingbad.ui.home.BBAdapter
import kotlinx.coroutines.flow.collect


class SearchFragment : BaseFragment<FragmentSearchBinding>(FragmentSearchBinding::inflate) {

    private lateinit var searchAdapter: BBAdapter
    private val viewModel: SearchViewModel by viewModels()

    private val emptyList = mutableListOf<BBCharacter>()
    private var fullList = mutableListOf<BBCharacter>()

    override fun start() {
//        setRecycler()
//        getBBCharactersFromRoom()
//        search()
    }


//    private fun getBBCharactersFromRoom() {
//        lifecycleScope.launchWhenStarted {
//            viewModel.loadCharacters.collect {
//                fullList = it.toMutableList()
//            }
//        }
//    }

    private fun setRecycler() {
        searchAdapter = BBAdapter {
            val action = CharacterDetailsFragmentDirections.toCharacterDetailsFragment(it)
            activity?.findNavController(R.id.mainContainer)?.navigate(action)
        }

        binding.recycler.adapter = searchAdapter
        binding.recycler.layoutManager = GridLayoutManager(requireContext(), 2)
        searchAdapter.setData(emptyList)
    }

    private fun search() {
        fun filterText(text: String) {
            val filteredList = mutableListOf<BBCharacter>()



            for (item in fullList) {
                if (item.name.lowercase().contains(text.lowercase()) || item.nickname.lowercase()
                        .contains(text.lowercase()) || item.portrayed.lowercase()
                        .contains(text.lowercase())
                ) {
                    filteredList.add(item)
                }
            }
            searchAdapter.filteredList(filteredList)
        }

        binding.etSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                //
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

                filterText(p0.toString())
            }

            override fun afterTextChanged(p0: Editable?) {
                if (TextUtils.isEmpty(p0.toString())){
                    searchAdapter.setData(emptyList)
                }
//                filterText(p0.toString())

            }
        })

    }
}