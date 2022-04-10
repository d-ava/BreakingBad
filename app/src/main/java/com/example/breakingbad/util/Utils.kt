package com.example.breakingbad.util

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import androidx.core.graphics.drawable.toDrawable
import com.example.breakingbad.R
import com.example.breakingbad.model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

object Utils {

    var auth: FirebaseAuth = FirebaseAuth.getInstance()
    var database = FirebaseDatabase.getInstance()

    var databaseReference = database.reference.child("user")


    var savedCharacterslist = ""
    var authUserInfo: User = User()

    fun showLoadingDialog(context: Context): Dialog {
        val progressDialog = Dialog(context)

        progressDialog.let {
            it.show()
            it.window?.setBackgroundDrawable(Color.TRANSPARENT.toDrawable())
            it.setContentView(R.layout.progress_dialog)
            it.setCancelable(false)
            it.setCanceledOnTouchOutside(true)

            return it
        }
    }

    fun convertStringToListOfInt(str:String):List<Int>{
        var newList0 = mutableListOf<String>()
        val newIntList0 = mutableListOf<Int>()

        newList0 = str.split(",").toMutableList()
        newList0.forEach { newIntList0.add(it.toInt()) }

        return newIntList0
    }

}