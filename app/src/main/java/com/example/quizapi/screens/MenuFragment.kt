package com.example.quizapi.screens
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.quizapi.MainActivity
import com.example.quizapi.R
import com.example.quizapi.databinding.FragmentMenuBinding

class MenuFragment: Fragment(R.layout.fragment_menu) {
    private lateinit var binding: FragmentMenuBinding
    private lateinit var preferences: SharedPreferences
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentMenuBinding.bind(view)
        preferences = requireActivity().getSharedPreferences(MainActivity.APP_PREFERENCE, Context.MODE_PRIVATE)
        val score = preferences.getInt(MainActivity.SCORE_VALUE, 0)
        binding.highScoreTextView.text = "High Score: $score"
        binding.startButton.setOnClickListener{
        findNavController().navigate(R.id.action_menuFragment_to_gameFragment)
        }
    }
}