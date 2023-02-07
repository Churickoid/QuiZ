package com.example.quizapi.model.repository

import com.example.quizapi.data.ApiInterface
import com.example.quizapi.model.Theme

class ThemesRepository (private val retrofit: ApiInterface){

    private var themes = mutableListOf<Theme>()

   suspend fun getThemes(): List<Theme>{
       if (themes.isEmpty()){
           val mapThemes = retrofit.getThemesList()
           var count = 0
           for (i in mapThemes) {
               val maxLen = i.value.maxBy {it.length }
               themes+= Theme(i.key,maxLen,true, count++)
           }
       }

       return themes
   }

    fun changeActiveById(id :Int):List<Theme>{
        val changedTheme = themes[id].copy(isActive = !themes[id].isActive)
        themes = ArrayList(themes)
        themes[id] = changedTheme
        return themes
    }

    fun unselectAllActive():List<Theme>{
        val list = themes
        themes = arrayListOf()
        for (i in list){
            themes += i.copy(isActive = false)
        }
        return themes

    }

}