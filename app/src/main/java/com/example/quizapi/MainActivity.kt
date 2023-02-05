package com.example.quizapi

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.quizapi.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
   
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
    companion object{
        const val APP_PREFERENCE = "APP_PREFERENCE"
        const val SCORE_VALUE = "SCORE_VALUE"
    }
}