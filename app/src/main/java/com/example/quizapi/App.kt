package com.example.quizapi

import android.app.Application
import com.example.quizapi.utils.DIContainer

class App: Application() {
    val container = DIContainer()
}