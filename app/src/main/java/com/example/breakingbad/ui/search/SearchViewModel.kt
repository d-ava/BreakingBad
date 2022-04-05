package com.example.breakingbad.ui.search

import androidx.lifecycle.ViewModel
import com.example.breakingbad.db.BBDao
import com.example.breakingbad.db.BBDatabase
import com.example.breakingbad.model.BBCharacter
import kotlinx.coroutines.flow.Flow

class SearchViewModel:ViewModel() {

    private var dao: BBDao = BBDatabase.db.bbDao()

    var loadCharacters: Flow<List<BBCharacter>> = dao.getAllCharacters() //<-------------


}