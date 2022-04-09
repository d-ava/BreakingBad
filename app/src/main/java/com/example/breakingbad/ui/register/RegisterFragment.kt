package com.example.breakingbad.ui.register

import android.util.Log
import androidx.navigation.fragment.findNavController
import com.example.breakingbad.databinding.FragmentRegisterBinding
import com.example.breakingbad.model.User
import com.example.breakingbad.ui.BaseFragment
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase

class RegisterFragment : BaseFragment<FragmentRegisterBinding>(FragmentRegisterBinding::inflate) {


    private var auth = Firebase.auth


    var database = FirebaseDatabase.getInstance()
    var databaseReference = database.reference.child("user")


    override fun start() {
        setListeners()


    }

    private fun registerUser() {

        var charList: MutableList<String> = mutableListOf()

        auth.createUserWithEmailAndPassword(
            binding.etEmail.text.toString(),
            binding.etPassword.text.toString()
        ).addOnCompleteListener(requireActivity()) {
            if (it.isSuccessful) {

                Log.d("---", "registration successful")
                val newUser = User(
                    name = binding.etName.text.toString(),
                    email = binding.etEmail.text.toString(),
                    characterId = mutableListOf("0")
                )
                databaseReference.child(auth.uid!!).setValue(newUser)


            } else {
                Log.d("---", "problem during registration")
            }


        }


    }

    private fun setListeners() {
        binding.apply {
            backArrow.setOnClickListener {
                findNavController().popBackStack()
            }
            btnLogin.setOnClickListener {
                registerUser()
            }


        }
    }
}