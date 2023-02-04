package com.example.quizapi.screens

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.quizapi.App
import com.example.quizapi.screens.game.GameViewModel
import com.example.quizapi.screens.menu.MenuViewModel
import java.lang.IllegalStateException

class ViewModelFactory(private val app: App) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        val viewModel = when(modelClass){
            GameViewModel :: class.java -> {
                GameViewModel(app.container.questionRepository)
            }
            MenuViewModel :: class.java -> {
                MenuViewModel(app.container.themesRepository)
            }
            else ->{
                throw IllegalStateException("Unknown View model class")
            }
        }
        return viewModel as T
    }

}
fun Fragment.factory() = ViewModelFactory(requireContext().applicationContext as App)