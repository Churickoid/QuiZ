package com.example.quizapi.screens.game

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.service.autofill.OnClickAction
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.quizapi.MainActivity.Companion.APP_PREFERENCE
import com.example.quizapi.MainActivity.Companion.SCORE_VALUE
import com.example.quizapi.R
import com.example.quizapi.databinding.FragmentGameBinding
import com.example.quizapi.navigation.navigator
import com.example.quizapi.screens.factory
import java.util.concurrent.TimeoutException

class GameFragment: Fragment(), View.OnClickListener,View.OnLongClickListener{
    private lateinit var binding: FragmentGameBinding
    private val viewModel: GameViewModel by viewModels{ factory() }
    private lateinit var preferences:SharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_game, container, false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        preferences =
            requireActivity().getSharedPreferences(APP_PREFERENCE, Context.MODE_PRIVATE)
        binding = FragmentGameBinding.bind(view)

        binding.answer1Button.setOnClickListener(this)
        binding.answer2Button.setOnClickListener(this)
        binding.answer3Button.setOnClickListener(this)
        binding.answer4Button.setOnClickListener(this)

        binding.errorButton.setOnClickListener(this)

        if (viewModel.getResetValue()){
            viewModel.startSession()
        }

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
        viewModel.panel.observe(viewLifecycleOwner) {
            when (it) {
                0 -> {
                    binding.loading.visibility = View.VISIBLE
                    binding.buttonsLL.visibility = View.INVISIBLE
                    binding.errorButton.visibility = View.INVISIBLE
                }
                1 -> {
                    binding.loading.visibility = View.INVISIBLE
                    binding.buttonsLL.visibility = View.VISIBLE
                    binding.errorButton.visibility = View.INVISIBLE
                }
                2 -> {
                    binding.loading.visibility = View.INVISIBLE
                    binding.buttonsLL.visibility = View.INVISIBLE
                    binding.errorButton.visibility = View.VISIBLE
                }
                else -> throw Exception("Unknown value")
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
            if(it>=0) {
                if (it > preferences.getInt(SCORE_VALUE, 0))
                    preferences.edit()
                        .putInt(SCORE_VALUE, it)
                        .apply()

                navigator().openEndScreen(it)
            }
        }
        viewModel.disableButtons.observe(viewLifecycleOwner){
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
            R.id.errorButton -> viewModel.loadRound()
            else -> throw TimeoutException("No Element")
        }
    }

    override fun onLongClick(p0: View?): Boolean {
        return false
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