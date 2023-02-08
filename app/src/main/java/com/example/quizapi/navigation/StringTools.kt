package com.example.quizapi.navigation

import androidx.fragment.app.Fragment

interface StringTools {
    fun changeTitle(title: String)
}

fun Fragment.stringTools(): StringTools {
    return requireActivity() as StringTools
}