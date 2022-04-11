package com.example.breakingbad.util

import android.content.Context
import androidx.appcompat.app.AppCompatActivity

var lang=""

open class LanguageAwareActivity:AppCompatActivity() {

    override fun attachBaseContext(newBase: Context?) {

        val newLangContext = newBase?.let { updateLocale(it, lang) }

        super.attachBaseContext(newLangContext)
    }


}