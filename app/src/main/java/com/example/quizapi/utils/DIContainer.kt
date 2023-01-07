package com.example.quizapi.utils

import com.example.quizapi.data.ApiInterface
import com.example.quizapi.model.QuestionRepository
import com.example.quizapi.data.LocalDataSource
import com.example.quizapi.data.RemoteDataSource
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


     val retrofit = Retrofit.Builder()
        .baseUrl("https://the-trivia-api.com")
        .addConverterFactory(GsonConverterFactory.create())
        .client(okHttpClient)
        .build()
        .create(ApiInterface::class.java)

    private val localDataSource = LocalDataSource()
    private val remoteDataSource = RemoteDataSource(retrofit)
    val questionRepository = QuestionRepository(remoteDataSource)
}