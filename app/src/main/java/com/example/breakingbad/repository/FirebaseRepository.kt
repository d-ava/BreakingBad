package com.example.breakingbad.repository

import android.util.Patterns
import com.example.breakingbad.model.User
import com.example.breakingbad.util.Resource
import com.example.breakingbad.util.Utils.auth
import com.example.breakingbad.util.Utils.databaseReference
import com.google.firebase.FirebaseNetworkException
import com.google.firebase.auth.AuthResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.tasks.await
import java.io.IOException
import javax.inject.Inject

class FirebaseRepository @Inject constructor() {

    fun saveCharacter(id:Int):Flow<Resource<Boolean>>{
        val user = auth.currentUser
        val userReference=databaseReference.child(user?.uid!!)

        return flow{
            try {
                userReference.child("characterId").setValue(mutableListOf(id, 9, 7)).await()
            emit(Resource.Success())
            }catch (e:IOException){
                emit(Resource.Error(e.message?:"unknown error"))
            }
        }
    }


    suspend fun logInUser(
        email: String,
        password: String
    ): Flow<Resource<AuthResult>> {
        return flow {
            try {
                emit(Resource.Loading())
                val result = auth.signInWithEmailAndPassword(email, password).await()
            emit(Resource.Success(result))
            } catch (e:FirebaseNetworkException){
                emit(Resource.Error(e.message ?: "network error"))
            }catch (e:Exception){
                emit(Resource.Error(e.message?:"unknown error"))
            }


        }.flowOn(Dispatchers.IO)
    }

    suspend fun registerUser(
        name: String,
        email: String,
        password: String,
        repeatPassword: String
    ): Flow<Resource<AuthResult>> {
        return flow {
            if (name.isNotEmpty() && email.isNotEmpty() && password.isNotEmpty() && repeatPassword.isNotEmpty()) {
                if (Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    if (password == repeatPassword) {
                        try {
                            emit(Resource.Loading())
                            val registrationResult =
                                auth.createUserWithEmailAndPassword(email, password).await()

                            val userId = registrationResult.user?.uid!!
                            val newUser = User(
                                name = name,
                                email = email,
                                characterIdsList = mutableListOf()
                            )
                            databaseReference.child(userId).setValue(newUser)
                            emit(Resource.Success(registrationResult))


                        } catch (e: FirebaseNetworkException) {
                            emit(Resource.Error(e.message ?: "network connection error"))
                        } catch (e: Exception) {
                            emit(Resource.Error(e.message ?: "unknown error"))
                        }
                    } else {
                        emit(Resource.Error("password mismatching"))
                    }
                } else {
                    emit(Resource.Error("enter valid email"))
                }
            } else {
                emit(Resource.Error("fields are empty"))
            }
        }.flowOn(Dispatchers.IO)
    }


}