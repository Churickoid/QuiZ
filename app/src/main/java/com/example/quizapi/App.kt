package com.example.quizapi

import android.app.Application
import com.example.quizapi.DI.DIContainer

class App: Application() {
    val container = DIContainer()
}