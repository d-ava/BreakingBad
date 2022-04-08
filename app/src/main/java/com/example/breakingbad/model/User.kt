package com.example.breakingbad.model

data class User(
    val name: String = "",
    val email: String = "",
    val characterId: List<String> = listOf()
)
