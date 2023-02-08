package com.example.quizapi.model.repository


import com.example.quizapi.data.ApiInterface
import com.example.quizapi.model.Round


class QuestionRepository(
    private val retrofit: ApiInterface
) {

    private var questionList = listOf<Round>()
    private var currentRequest = ""
    private var counter = 0

    suspend fun getQuestion(requestThemes: String): Round {
        if (counter >= 50 || questionList.isEmpty() || currentRequest != requestThemes) {
            getListOfQuestions(requestThemes)
        }
        val round = questionList[counter]
        round.answers += round.correct
        round.answers.shuffle()
        counter++
        return round
    }

    private suspend fun getListOfQuestions(requestThemes: String) {
        questionList = if (requestThemes == "") retrofit.getQuestionList()
        else retrofit.getQuestionList(themes = requestThemes)
        currentRequest = requestThemes
        counter = 0
    }

}
