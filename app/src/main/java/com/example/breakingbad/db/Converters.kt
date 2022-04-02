package com.example.breakingbad.db

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.squareup.moshi.Moshi
import retrofit2.converter.moshi.MoshiConverterFactory

class Converters {
    @TypeConverter
    fun listOfStringsToJson(value: List<String>?): String = Gson().toJson(value)

    @TypeConverter
    fun jsonToListOfStrings(value:String) = Gson().fromJson(value, Array<String>::class.java).toList()


    @TypeConverter
    fun listOfIntsToJson(value: List<Int>?):String = Gson().toJson(value)

    @TypeConverter
    fun jsonToListOfInts(value:String) = Gson().fromJson(value, Array<Int>::class.java).toList()

}