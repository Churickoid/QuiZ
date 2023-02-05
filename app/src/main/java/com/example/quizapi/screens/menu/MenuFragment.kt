package com.example.quizapi.screens.menu
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.quizapi.MainActivity
import com.example.quizapi.R
import com.example.quizapi.databinding.FragmentMenuBinding
import com.example.quizapi.screens.factory
import com.example.quizapi.screens.game.GameViewModel

class MenuFragment: Fragment(R.layout.fragment_menu) {

    private lateinit var binding: FragmentMenuBinding
    private lateinit var preferences: SharedPreferences
    private lateinit var themesAdapter: ThemesAdapter
    private val viewModel: MenuViewModel by viewModels{ factory() }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentMenuBinding.bind(view)

        preferences = requireActivity().getSharedPreferences(MainActivity.APP_PREFERENCE, Context.MODE_PRIVATE)
        val score = preferences.getInt(MainActivity.SCORE_VALUE, 0)
        binding.highScoreTextView.text = "High Score: $score"


        binding.startButton.setOnClickListener{
            findNavController().navigate(R.id.action_menuFragment_to_gameFragment)
        }
        setupRecyclerView()
        viewModel.themesList.observe(viewLifecycleOwner){
            themesAdapter.themeList = it
        }
        themesAdapter.onItemClickListener = {
            viewModel.changeItem(it)
        }
    }

    private fun setupRecyclerView(){
        themesAdapter = ThemesAdapter()
        binding.themesRecyclerView.adapter = themesAdapter
    }
}