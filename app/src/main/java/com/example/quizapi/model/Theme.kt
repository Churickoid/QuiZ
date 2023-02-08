package com.example.quizapi.model

data class Theme(
    val themeName: String,
    val themeRequest: String,
    var isActive: Boolean,
    val id: Int
)