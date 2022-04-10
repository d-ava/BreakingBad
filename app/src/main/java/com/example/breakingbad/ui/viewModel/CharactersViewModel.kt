package com.example.breakingbad.ui.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.breakingbad.model.BBCharacter
import com.example.breakingbad.model.User
import com.example.breakingbad.repository.CharactersRepository
import com.example.breakingbad.repository.FirebaseRepository
import com.example.breakingbad.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CharactersViewModel @Inject constructor(private val repository: CharactersRepository, private val firebaseRepository: FirebaseRepository) :
    ViewModel() {


    val loadCharacters: SharedFlow<Resource<List<BBCharacter>>?> =
        repository.getCharacters().shareIn(viewModelScope, SharingStarted.WhileSubscribed())


    //load saved characters

    private val _loadSavedCharactersList: MutableSharedFlow<User> = MutableSharedFlow()
    val loadSavedCharactersList:SharedFlow<User> = _loadSavedCharactersList

    fun loadSavedCharacters(){
        viewModelScope.launch {
            firebaseRepository.loadSavedCharactersList(_loadSavedCharactersList)
        }
    }

    private val _loadCharactersById:MutableSharedFlow<Resource<BBCharacter>> = MutableSharedFlow()
    val loadCharactersById:SharedFlow<Resource<BBCharacter>> = _loadCharactersById

    fun loadCharactersById(id:Int){
        viewModelScope.launch {
            repository.getCharacterById(id).collect {
                _loadCharactersById.emit(it!!)
            }
        }
    }


}