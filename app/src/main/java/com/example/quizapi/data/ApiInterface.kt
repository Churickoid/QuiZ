package com.example.quizapi.data

import com.example.quizapi.model.Round
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiInterface{
    @GET("api/questions")
    suspend fun getQuestionList(@Query("limit") num:Int = 1 ): List<Round>

    @GET("api/categories")
    suspend fun getThemesList(@Query("limit") num:Int = 1 ): Map<String, Array<String>>

}