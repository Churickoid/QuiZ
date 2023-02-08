package com.example.quizapi.di

import com.example.quizapi.data.ApiInterface
import com.example.quizapi.model.repository.QuestionRepository
import com.example.quizapi.model.repository.ThemesRepository
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class DIContainer {

    private val httpLoggingInterceptor = HttpLoggingInterceptor()
        .setLevel(HttpLoggingInterceptor.Level.BODY)

    private val okHttpClient = OkHttpClient
        .Builder()
        .addInterceptor(httpLoggingInterceptor)
        .build()


    private val retrofit = Retrofit.Builder()
        .baseUrl("https://the-trivia-api.com")
        .addConverterFactory(GsonConverterFactory.create())
        .client(okHttpClient)
        .build()
        .create(ApiInterface::class.java)

    val questionRepository = QuestionRepository(retrofit)
    val themesRepository = ThemesRepository(retrofit)
}