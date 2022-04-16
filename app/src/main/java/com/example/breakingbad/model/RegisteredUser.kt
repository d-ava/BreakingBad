package com.example.breakingbad.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class RegisteredUser(val email:String?=null, val password:String?=null):Parcelable
