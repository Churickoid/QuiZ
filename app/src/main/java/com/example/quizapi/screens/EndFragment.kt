package com.example.quizapi.screens

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.quizapi.MainActivity
import com.example.quizapi.MainActivity.Companion.SCORE_VALUE
import com.example.quizapi.R
import com.example.quizapi.databinding.FragmentEndBinding
import com.example.quizapi.databinding.FragmentMenuBinding
import com.example.quizapi.navigation.navigator

class EndFragment: Fragment() {
    private lateinit var binding: FragmentEndBinding
    private lateinit var preferences: SharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_end, container, false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentEndBinding.bind(view)

        val currentScore = arguments?.getInt(CURRENT_SCORE) ?: 0
        binding.currentScoreTextView.text = "Current Score: $currentScore"

        preferences = requireActivity().getSharedPreferences(MainActivity.APP_PREFERENCE, Context.MODE_PRIVATE)
        val highScore = preferences.getInt(SCORE_VALUE, 0)
        binding.highScoreTextView.text = "High Score: $highScore"

        binding.menuButton.setOnClickListener{
            navigator().goToMenu()
        }

        binding.restartButton.setOnClickListener{
            navigator().goBack()
        }
    }
    fun newInstance(result: Int): EndFragment {
        val args = Bundle()
        args.putInt(CURRENT_SCORE, result)
        val fragment = EndFragment()
        fragment.arguments = args
        return fragment
    }
    companion object{

        private const val CURRENT_SCORE = "CURRENT_SCORE"

    }
}