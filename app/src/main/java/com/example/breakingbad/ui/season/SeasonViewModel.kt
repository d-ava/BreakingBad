package com.example.breakingbad.ui.season

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.breakingbad.model.BBEpisodes
import com.example.breakingbad.repository.EpisodesRepository
import com.example.breakingbad.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SeasonViewModel @Inject constructor(private val repository: EpisodesRepository) :
    ViewModel() {

    private val _getEpisodes: MutableSharedFlow<Resource<List<BBEpisodes>>> = MutableSharedFlow()
    val getEpisodes: SharedFlow<Resource<List<BBEpisodes>>> = _getEpisodes

    init {
        fetchEpisodes()
    }
    private fun fetchEpisodes(){
        viewModelScope.launch {
            repository.getEpisodes().collect {
                _getEpisodes.emit(it!!)
            }
        }
    }



}