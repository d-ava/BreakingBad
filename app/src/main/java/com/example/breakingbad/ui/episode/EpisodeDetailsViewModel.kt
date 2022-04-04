package com.example.breakingbad.ui.episode

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.breakingbad.db.BBDao
import com.example.breakingbad.db.BBDatabase
import com.example.breakingbad.db.EpisodesDao
import com.example.breakingbad.db.EpisodesDatabase
import com.example.breakingbad.model.BBCharacter
import com.example.breakingbad.model.BBQuotes
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class EpisodeDetailsViewModel : ViewModel() {

    private var dao: BBDao = BBDatabase.db.bbDao()



    var loadCharacters: Flow<List<BBCharacter>> = dao.getAllCharacters() //<-------------

}






