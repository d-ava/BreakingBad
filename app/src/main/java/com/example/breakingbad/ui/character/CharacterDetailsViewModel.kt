package com.example.breakingbad.ui.character

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.breakingbad.api.NetworkClient
import com.example.breakingbad.db.EpisodesDao
import com.example.breakingbad.db.EpisodesDatabase
import com.example.breakingbad.db.QuotesDao
import com.example.breakingbad.db.QuotesDatabase
import com.example.breakingbad.model.BBQuotes
import com.example.breakingbad.model.User
import com.example.breakingbad.repository.FirebaseRepository
import com.example.breakingbad.repository.QuotesRepository
import com.example.breakingbad.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class CharacterDetailsViewModel @Inject constructor(
    private val repository: QuotesRepository,
    private val firebaseRepository: FirebaseRepository
) :
    ViewModel() {

    val loadQuotes: SharedFlow<Resource<List<BBQuotes>>?> =
        repository.getQuotes().shareIn(viewModelScope, SharingStarted.WhileSubscribed())


//val loadQuotes = repository.getQuotes()

    private val _fetchQuotes = MutableLiveData<Resource<List<BBQuotes>>>()
    val fetchQuotes = _fetchQuotes

    init {
        fetchQuotes()
    }

    private fun fetchQuotes() {
        viewModelScope.launch {
            repository.getQuotes().collect {
                _fetchQuotes.value = it
            }
        }
    }

    private val _saveCharacter:MutableSharedFlow<Resource<Boolean>> = MutableSharedFlow()
    val saveCharacter:SharedFlow<Resource<Boolean>> = _saveCharacter

    fun saveCharacterId(newListInt:String){
        viewModelScope.launch {
            firebaseRepository.saveCharacter(newListInt).collect {
                _saveCharacter.emit(it)
            }
        }
    }




}