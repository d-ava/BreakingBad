package com.example.breakingbad.ui.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.breakingbad.repository.FirebaseRepository
import com.example.breakingbad.util.Resource
import com.google.firebase.auth.AuthResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(private val repository: FirebaseRepository):ViewModel() {

    private val _logIn:MutableSharedFlow<Resource<AuthResult>> = MutableSharedFlow()
    val login:SharedFlow<Resource<AuthResult>> = _logIn

    fun logInUser(email:String, password:String){
        viewModelScope.launch {
            repository.logInUser(email,password).collect {
                _logIn.emit(it)
            }
        }


    }



}