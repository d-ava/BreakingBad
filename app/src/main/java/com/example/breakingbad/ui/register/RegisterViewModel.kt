package com.example.breakingbad.ui.register

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
class RegisterViewModel @Inject constructor(private val repository: FirebaseRepository) :
    ViewModel() {

    private val _userRegister:MutableSharedFlow<Resource<AuthResult>> = MutableSharedFlow()
    val userRegister:SharedFlow<Resource<AuthResult>> = _userRegister


        fun registerUser(
            name:String,
            email:String,
            password:String,
            repeatPassword:String
        ){
            viewModelScope.launch {
                _userRegister.emit(Resource.Loading())
                repository.registerUser(name, email, password, repeatPassword).collect {
                    _userRegister.emit(it)
                }
            }
        }


}