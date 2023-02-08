package com.example.quizapi.model

import com.google.gson.annotations.SerializedName

data class Round(
    @SerializedName("category")
    val category: String,
    @SerializedName("question")
    val question: String,
    @SerializedName("correctAnswer")
    val correct: String,
    @SerializedName("incorrectAnswers")
    val answers: MutableList<String>
)
