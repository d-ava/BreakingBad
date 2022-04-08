package com.example.breakingbad.ui.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.breakingbad.model.BBCharacter
import com.example.breakingbad.repository.CharactersRepository
import com.example.breakingbad.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.shareIn
import javax.inject.Inject

@HiltViewModel
class CharactersViewModel @Inject constructor(private val repository: CharactersRepository) :
    ViewModel() {


    val loadCharacters: SharedFlow<Resource<List<BBCharacter>>> =
        repository.getCharacters().shareIn(viewModelScope, SharingStarted.WhileSubscribed())


}