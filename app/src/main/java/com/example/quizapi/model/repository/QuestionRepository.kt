package com.example.quizapi.model.repository


import com.example.quizapi.data.ApiInterface
import com.example.quizapi.model.Round


class QuestionRepository(
    private val retrofit: ApiInterface
) {

    suspend fun getQuestion(requestThemes: String): Round {
        val response = if(requestThemes=="") retrofit.getQuestionList()
        else retrofit.getQuestionList(requestThemes)

        val round = response[0]
        round.answers += round.correct
        round.answers.shuffle()
        return round
    }

}
