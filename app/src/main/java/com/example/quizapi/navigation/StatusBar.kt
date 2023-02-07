package com.example.quizapi.navigation

import androidx.fragment.app.Fragment

interface StatusBar {

    fun changeTitle(title:String)
}

fun Fragment.statusBar(): StatusBar {
    return requireActivity() as StatusBar
}