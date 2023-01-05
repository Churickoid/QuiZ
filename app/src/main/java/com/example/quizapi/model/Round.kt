package com.example.quizapi.model

data class Round(
    val category : String,
    val question: String,
    val correct: String,
    val answers: List<String>
)
