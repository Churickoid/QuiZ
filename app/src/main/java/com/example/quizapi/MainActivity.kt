package com.example.quizapi

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.NavHostFragment
import com.example.quizapi.databinding.ActivityMainBinding
import com.example.quizapi.screens.game.GameViewModel
import com.example.quizapi.screens.factory


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
   
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}