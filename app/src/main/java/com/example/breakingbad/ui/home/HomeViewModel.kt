package com.example.breakingbad.ui.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.breakingbad.api.NetworkClient
import com.example.breakingbad.db.BBDao
import com.example.breakingbad.db.BBDatabase
import com.example.breakingbad.db.QuotesDao
import com.example.breakingbad.db.QuotesDatabase
import com.example.breakingbad.model.BBCharacter
import com.example.breakingbad.model.BBQuotes
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HomeViewModel : ViewModel() {

    private var dao: BBDao = BBDatabase.db.bbDao()
    private var quotesDao: QuotesDao = QuotesDatabase.dbQuotes.quotesDao()

    var loadCharacters: Flow<List<BBCharacter>> = dao.getAllCharacters()
    var loadQuotes: Flow<List<BBQuotes>> = quotesDao.getAllQuotes()

    init {

        getCharacters()
        getQuotes()

    }

    private fun getQuotes(){
        viewModelScope.launch {
            withContext(Dispatchers.IO){
                val response = NetworkClient.bbQuotesApi.getQuotes()
                val body = response.body()
                if (response.isSuccessful && body != null){
                    quotesDao.insertQuotes(body)
                }
            }
        }
    }

    private fun getCharacters() {

        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                val response = NetworkClient.bbCharactersApi.getBBCharacters()
                val body = response.body()
                if (response.isSuccessful && body != null) {

                    dao.insertCharacters(body)
                }
            }

        }

    }


}