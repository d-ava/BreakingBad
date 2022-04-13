package com.example.breakingbad

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.breakingbad.util.LanguageAwareActivity
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : LanguageAwareActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }




}