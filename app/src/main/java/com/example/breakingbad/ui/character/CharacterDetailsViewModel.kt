package com.example.breakingbad.ui.character

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.breakingbad.api.NetworkClient
import com.example.breakingbad.db.EpisodesDao
import com.example.breakingbad.db.EpisodesDatabase
import com.example.breakingbad.db.QuotesDao
import com.example.breakingbad.db.QuotesDatabase
import com.example.breakingbad.model.BBQuotes
import com.example.breakingbad.repository.QuotesRepository
import com.example.breakingbad.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class CharacterDetailsViewModel @Inject constructor(private val repository: QuotesRepository) :
    ViewModel() {

        val loadQuotes: SharedFlow<Resource<List<BBQuotes>>?> =
            repository.getQuotes().shareIn(viewModelScope, SharingStarted.WhileSubscribed())


//    private val quotesDao: QuotesDao = QuotesDatabase.dbQuotes.quotesDao()
//    private val episodesDao: EpisodesDao = EpisodesDatabase.dbEpisodes.episodesDao()

//    private val _getQuotes: MutableSharedFlow<List<BBQuotes>> = MutableSharedFlow()
//    val getQuotes: SharedFlow<List<BBQuotes>> = _getQuotes

//    fun getQuotesFromAuthor(author: String) {
//        viewModelScope.launch {
//            withContext(Dispatchers.IO) {
//                quotesDao.getAllQuotesFromAuthor(author).collect {
//                    _getQuotes.emit(it)
//                }
//
//            }
//        }
//    }

//    init {
//        getAllEpisodes()
//    }


//private     fun getAllEpisodes() {
//        viewModelScope.launch {
//            withContext(Dispatchers.IO) {
//                val response = NetworkClient.bbEpisodesApi.getEpisodes()
//                val body = response.body()
//                if (response.isSuccessful && body !=null){
//                    episodesDao.insertEpisodes(body)
//                }
//            }
//        }
//    }


}