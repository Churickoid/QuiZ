package com.example.quizapi.screens

import android.app.Activity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.quizapi.App
import com.example.quizapi.screens.MainViewModel
import java.lang.IllegalStateException

class ViewModelFactory(private val app: App) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        val viewModel = when(modelClass){
            MainViewModel :: class.java -> {
                MainViewModel(app.container.questionRepository)
            }
            else ->{
                throw IllegalStateException("Unknown View model class")
            }
        }
        return viewModel as T
    }

}
fun Activity.factory() = ViewModelFactory(applicationContext as App)