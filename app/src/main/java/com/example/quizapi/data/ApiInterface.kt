package com.example.quizapi.data

import com.example.quizapi.model.Round
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiInterface{
    @GET("api/questions")
    suspend fun getQuestionList(@Query("categories") themes:String = "general_knowledge,arts_and_literature,film_and_tv,food_and_drink,geography,history,science,music,society_and_culture,sport_and_leisure" ): List<Round>

    @GET("api/categories")
    suspend fun getThemesList(): Map<String, Array<String>>

}