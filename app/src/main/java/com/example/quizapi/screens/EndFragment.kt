package com.example.quizapi.screens

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.quizapi.R
import com.example.quizapi.databinding.FragmentEndBinding
import com.example.quizapi.databinding.FragmentMenuBinding

class EndFragment: Fragment(R.layout.fragment_end) {
    private lateinit var binding: FragmentEndBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentEndBinding.bind(view)
        binding.menuButton.setOnClickListener{
            findNavController().popBackStack(R.id.menuFragment,false)
        }
        binding.restartButton.setOnClickListener{
            findNavController().navigate(R.id.action_endFragment_to_gameFragment)
        }
    }
}