package com.example.quizapi.navigation

import androidx.fragment.app.Fragment


interface Navigator {
    fun openEndScreen(currentScore:Int)
    fun openGameScreen()
    fun goBack()
    fun goToMenu()
}
fun Fragment.navigator(): Navigator {
    return requireActivity() as Navigator
}