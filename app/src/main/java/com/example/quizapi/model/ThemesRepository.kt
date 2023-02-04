package com.example.quizapi.model

import android.util.Log
import com.example.quizapi.data.ApiInterface

class ThemesRepository (private val retrofit: ApiInterface){

    private var themes = mutableListOf<Theme>()

   suspend fun getThemes(): List<Theme>{
       val mapThemes = retrofit.getThemesList()
       var count = 0
       for (i in mapThemes) {
           val maxLen = i.value.maxBy {it.length }
           themes+= Theme(i.key,maxLen,true, count++)
       }
       return themes
    }

    fun changeActiveById(id :Int):List<Theme>{
        themes[id].isActive = !themes[id].isActive
        return themes
    }

}