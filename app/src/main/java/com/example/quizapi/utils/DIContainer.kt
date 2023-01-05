package com.example.quizapi.utils

import com.example.quizapi.model.QuestionRepository
import com.example.quizapi.data.LocalDataSource

class DIContainer {
    private val localDataSource = LocalDataSource()
    val questionRepository = QuestionRepository(localDataSource)
}