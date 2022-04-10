package com.example.breakingbad.repository

import android.util.Patterns
import com.example.breakingbad.model.User
import com.example.breakingbad.util.Resource
import com.example.breakingbad.util.Utils.auth
import com.example.breakingbad.util.Utils.databaseReference
import com.example.breakingbad.util.Utils.savedCharacterslist
import com.google.firebase.FirebaseNetworkException
import com.google.firebase.auth.AuthResult
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import java.io.IOException
import javax.inject.Inject

class FirebaseRepository @Inject constructor() {

    fun saveCharacter(id: String): Flow<Resource<Boolean>> {
        val user = auth.currentUser
        val userReference = databaseReference.child(user?.uid!!)

        return flow {
            try {
//                var newTestList:MutableList = mutableListOf()
                userReference.child("characterId").setValue(id).await()
                emit(Resource.Success())
            } catch (e: IOException) {
                emit(Resource.Error(e.message ?: "unknown error"))
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
            } catch (e: FirebaseNetworkException) {
                emit(Resource.Error(e.message ?: "network error"))
            } catch (e: Exception) {
                emit(Resource.Error(e.message ?: "unknown error"))
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
                                characterId = mutableListOf(0,0)
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

    suspend fun loadSavedCharactersList(userForFlow: MutableSharedFlow<User>) {
        var userInfo: User

        val user = auth.currentUser
        val userReference = databaseReference.child(user?.uid!!)

        userReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                userInfo = User(
                    name = snapshot.child("name").value.toString(),
                    email = auth.currentUser?.email.toString(),
                )
                savedCharacterslist = snapshot.child("characterId").value.toString()

                CoroutineScope(IO).launch {
                    userForFlow.emit(userInfo)
                }


            }

            override fun onCancelled(error: DatabaseError) {

            }
        })

    }


}