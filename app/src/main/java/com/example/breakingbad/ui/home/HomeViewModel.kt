package com.example.breakingbad.ui.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.breakingbad.api.NetworkClient
import com.example.breakingbad.db.BBDao
import com.example.breakingbad.db.BBDatabase
import com.example.breakingbad.model.BBCharacter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HomeViewModel:ViewModel() {

    private var dao:BBDao = BBDatabase.db.bbDao()

    var loadCharacters:Flow<List<BBCharacter>> = dao.getAllCharacters()

   init {


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