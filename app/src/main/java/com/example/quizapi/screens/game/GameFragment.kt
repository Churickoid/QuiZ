package com.example.quizapi.screens.game

import android.os.Bundle
import android.service.autofill.OnClickAction
import android.view.View
import android.widget.Button
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.quizapi.R
import com.example.quizapi.databinding.FragmentGameBinding
import com.example.quizapi.screens.factory

class GameFragment: Fragment(R.layout.fragment_game), View.OnClickListener{
    private lateinit var binding: FragmentGameBinding
    private val viewModel: GameViewModel by viewModels{ factory() }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentGameBinding.bind(view)

        binding.answer1Button.setOnClickListener(this)
        binding.answer2Button.setOnClickListener(this)
        binding.answer3Button.setOnClickListener(this)
        binding.answer4Button.setOnClickListener(this)

        binding.errorButton.setOnClickListener(this)

        viewModel.round.observe(viewLifecycleOwner){
            binding.categoryTextView.text = getString(R.string.category, it.category)
            binding.questionTextView.text = it.question
            binding.answer1Button.text = it.answers[0]
            binding.answer2Button.text = it.answers[1]
            binding.answer3Button.text = it.answers[2]
            binding.answer4Button.text = it.answers[3]
        }

        viewModel.lives.observe(viewLifecycleOwner){
            binding.lifeTextView.text = getString(R.string.lives, it)
        }
        viewModel.score.observe(viewLifecycleOwner){
            binding.scoreTextView.text = getString(R.string.total_score, it)
        }
        viewModel.timer.observe(viewLifecycleOwner){
            if (it >= 0){
                binding.timerTextView.text = it.toString()
                binding.timerProgressBar.progress = it
            }

        }
        viewModel.panel.observe(viewLifecycleOwner){
            if(it){
                binding.loading.visibility = View.INVISIBLE
                binding.buttonsLL.visibility = View.VISIBLE
            }
            else{
                binding.loading.visibility = View.VISIBLE
                binding.buttonsLL.visibility = View.INVISIBLE
            }
        }
        viewModel.error.observe(viewLifecycleOwner){
            if(it){
                binding.loading.visibility = View.INVISIBLE
                binding.errorButton.visibility = View.VISIBLE

            }
            else{
                binding.loading.visibility = View.VISIBLE
                binding.errorButton.visibility = View.INVISIBLE
            }
        }
        viewModel.buttonId.observe(viewLifecycleOwner){
            val currentButton = view.findViewById<Button>(getButtonId(it))
            when(it/4){
                0 -> currentButton.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.green))
                1 -> currentButton.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.red))
                else -> currentButton.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.purple_500))
            }
        }
        viewModel.end.observe(viewLifecycleOwner){
            if(it){
                findNavController().navigate(R.id.action_gameFragment_to_endFragment)
            }
        }
        viewModel.disabled.observe(viewLifecycleOwner){
            if(it){
                binding.answer1Button.isClickable = false
                binding.answer2Button.isClickable = false
                binding.answer3Button.isClickable = false
                binding.answer4Button.isClickable = false
            }
            else{
                binding.answer1Button.isClickable = true
                binding.answer2Button.isClickable = true
                binding.answer3Button.isClickable = true
                binding.answer4Button.isClickable = true
            }
        }

    }

    override fun onClick(v: View) {
        when(v.id){
            R.id.answer1Button -> viewModel.checkAnswer(0)
            R.id.answer2Button -> viewModel.checkAnswer(1)
            R.id.answer3Button -> viewModel.checkAnswer(2)
            R.id.answer4Button -> viewModel.checkAnswer(3)
            else -> viewModel.loadRound()
        }
    }

    private fun getButtonId(id: Int): Int{
        return when(id%4){
            0 -> R.id.answer1Button
            1 -> R.id.answer2Button
            2 -> R.id.answer3Button
            else -> R.id.answer4Button
        }

    }

}