package com.example.quizapi.screens

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.quizapi.MainActivity
import com.example.quizapi.MainActivity.Companion.SCORE_VALUE
import com.example.quizapi.R
import com.example.quizapi.databinding.FragmentEndBinding
import com.example.quizapi.databinding.FragmentMenuBinding

class EndFragment: Fragment(R.layout.fragment_end) {
    private lateinit var binding: FragmentEndBinding
    private lateinit var preferences: SharedPreferences
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentEndBinding.bind(view)

        preferences = requireActivity().getSharedPreferences(MainActivity.APP_PREFERENCE, Context.MODE_PRIVATE)
        val score = preferences.getInt(SCORE_VALUE, 0)
        binding.highScoreTextView.text = "High Score: $score"
        binding.menuButton.setOnClickListener{
            findNavController().popBackStack(R.id.menuFragment,false)
        }

        binding.restartButton.setOnClickListener{
            findNavController().navigate(R.id.action_endFragment_to_gameFragment)
        }
    }
}