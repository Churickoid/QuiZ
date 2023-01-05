package com.example.quizapi.model

import com.example.quizapi.data.DataSource

class QuestionRepository(
    private val dataSource: DataSource) {

    fun getQuestion():Round{
        return Round("Film & TV", "A skilled forger and conman is pursued by an FBI agent.","Catch Me If You Can",listOf("American History X","Logan","American Beauty","Catch Me If You Can").shuffled())
    }

}
