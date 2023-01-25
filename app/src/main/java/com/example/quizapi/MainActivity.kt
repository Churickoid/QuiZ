package com.example.quizapi

import android.opengl.Visibility
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.lifecycle.LifecycleOwner


import com.example.quizapi.databinding.ActivityMainBinding
import com.example.quizapi.utils.ViewModelFactory
import com.example.quizapi.utils.factory

class MainActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainViewModel by viewModels{ factory() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.answer1Button.setOnClickListener(this)
        binding.answer2Button.setOnClickListener(this)
        binding.answer3Button.setOnClickListener(this)
        binding.answer4Button.setOnClickListener(this)

        viewModel.round.observe(this){
            binding.categoryTextView.text = getString(R.string.category, it.category)
            binding.questionTextView.text = it.question
            binding.answer1Button.text = it.answers[0]
            binding.answer2Button.text = it.answers[1]
            binding.answer3Button.text = it.answers[2]
            binding.answer4Button.text = it.answers[3]
        }

        viewModel.lives.observe(this){
            binding.lifeTextView.text = getString(R.string.lives, it)
        }
        viewModel.score.observe(this){
            binding.scoreTextView.text = getString(R.string.total_score, it)
        }
        viewModel.timer.observe(this){
            binding.timerTextView.text = it.toString()
            binding.timerProgressBar.progress = it
        }
        viewModel.spinner.observe(this){
            if(it){
                binding.loading.visibility = View.VISIBLE
                binding.buttonsLL.visibility = View.INVISIBLE
            }
            else{
                binding.loading.visibility = View.INVISIBLE
                binding.buttonsLL.visibility = View.VISIBLE
            }
        }



    }

    override fun onClick(v: View) {
        viewModel.checkAnswer(when(v.id){
            R.id.answer1Button -> 0
            R.id.answer2Button -> 1
            R.id.answer3Button -> 2
            R.id.answer4Button -> 3
            else -> 4
        })
    }
}