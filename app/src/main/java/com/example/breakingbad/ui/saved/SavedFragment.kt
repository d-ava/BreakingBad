package com.example.breakingbad.ui.saved

import android.annotation.SuppressLint
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.breakingbad.R
import com.example.breakingbad.databinding.FragmentSavedBinding
import com.example.breakingbad.extensions.makeSnackbar
import com.example.breakingbad.model.BBCharacter
import com.example.breakingbad.ui.BaseFragment
import com.example.breakingbad.ui.character.CharacterDetailsFragmentDirections
import com.example.breakingbad.ui.character.intList
import com.example.breakingbad.ui.home.BBAdapter
import com.example.breakingbad.ui.viewModel.CharactersViewModel
import com.example.breakingbad.util.Resource
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SavedFragment : BaseFragment<FragmentSavedBinding>(FragmentSavedBinding::inflate) {

    private lateinit var searchAdapter: BBAdapter
    private val sharedViewModel: CharactersViewModel by activityViewModels()

    private var newSavedList = mutableListOf<BBCharacter>()

    override fun start() {

        setRecycler()
        getCharacters()

    }

    @SuppressLint("UnsafeRepeatOnLifecycleDetector")
    private fun getCharacters() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                sharedViewModel.loadCharacters.collect {
                    when (it) {
                        is Resource.Loading -> {
                            showLoading()
                        }
                        is Resource.Success -> {
                            hideLoading()

                            for (character in it.data!!) {
                                if (intList.contains(character.charId)) {
                                    newSavedList.add(character)
                                }
                            }
                            Log.d("---", "newsavedlist -> ${newSavedList.size}")
                            searchAdapter.setData(newSavedList)

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

    private fun setRecycler() {
        searchAdapter = BBAdapter {
            val action = CharacterDetailsFragmentDirections.toCharacterDetailsFragment(it)
            activity?.findNavController(R.id.mainContainer)?.navigate(action)
        }

        binding.recycler.adapter = searchAdapter
        binding.recycler.layoutManager = GridLayoutManager(requireContext(), 2)

    }


}