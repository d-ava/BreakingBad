package com.example.breakingbad.ui.character

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.breakingbad.db.QuotesDao
import com.example.breakingbad.db.QuotesDatabase
import com.example.breakingbad.model.BBQuotes
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CharacterDetailsViewModel : ViewModel() {

    private val quotesDao: QuotesDao = QuotesDatabase.dbQuotes.quotesDao()

    private val _getQuotes: MutableSharedFlow<List<BBQuotes>> = MutableSharedFlow()
    val getQuotes: SharedFlow<List<BBQuotes>> = _getQuotes

    fun getQuotesFromAuthor(author: String) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                quotesDao.getAllQuotesFromAuthor(author).collect {
                    _getQuotes.emit(it)
                }

            }
        }


    }


}