package com.example.breakingbad.ui.search

import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.example.breakingbad.databinding.FragmentSearchBinding
import com.example.breakingbad.model.BBCharacter
import com.example.breakingbad.ui.BaseFragment
import com.example.breakingbad.ui.home.BBAdapter
import kotlinx.coroutines.flow.collect


class SearchFragment : BaseFragment<FragmentSearchBinding>(FragmentSearchBinding::inflate) {

    private lateinit var searchAdapter: BBAdapter
    private val viewModel: SearchViewModel by viewModels()

    private val emptyList = mutableListOf<BBCharacter>()
    private var fullList = mutableListOf<BBCharacter>()

    override fun start() {
        setRecycler()
        getBBCharactersFromRoom()
        search()
    }


    private fun getBBCharactersFromRoom() {
        lifecycleScope.launchWhenStarted {
            viewModel.loadCharacters.collect {
                fullList = it.toMutableList()
            }
        }
    }

    private fun setRecycler() {
        searchAdapter = BBAdapter {
            //
        }

        binding.recycler.adapter = searchAdapter
        binding.recycler.layoutManager = GridLayoutManager(requireContext(), 3)
        searchAdapter.setData(emptyList)
    }

    private fun search(){
        fun filterText(text: String){
            val filteredList = mutableListOf<BBCharacter>()

            for (item in fullList){
                if (item.name.lowercase().contains(text.lowercase())){
                    filteredList.add(item)
                }
            }
            searchAdapter.filteredList(filteredList)
        }

        binding.etSearch.addTextChangedListener(object :TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                //
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
               //
            }

            override fun afterTextChanged(p0: Editable?) {
                filterText(p0.toString())

            }
        })

    }
}