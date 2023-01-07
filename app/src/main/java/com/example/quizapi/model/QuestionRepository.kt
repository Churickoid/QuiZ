package com.example.quizapi.model

import com.example.quizapi.data.DataSource

class QuestionRepository(
    private val dataSource: DataSource
) {
    private var questions: List<Round>? = null
     fun getQuestions(num:Int) {

    }

}
