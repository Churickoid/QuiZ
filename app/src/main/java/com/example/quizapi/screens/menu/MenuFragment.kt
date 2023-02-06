package com.example.quizapi.screens.menu
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.quizapi.MainActivity
import com.example.quizapi.R
import com.example.quizapi.databinding.FragmentMenuBinding
import com.example.quizapi.navigation.navigator
import com.example.quizapi.screens.factory

class MenuFragment: Fragment() {

    private lateinit var binding: FragmentMenuBinding
    private lateinit var preferences: SharedPreferences
    private lateinit var themesAdapter: ThemesAdapter
    private val viewModel: MenuViewModel by viewModels{ factory() }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_menu, container, false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentMenuBinding.bind(view)

        preferences = requireActivity().getSharedPreferences(MainActivity.APP_PREFERENCE, Context.MODE_PRIVATE)
        val score = preferences.getInt(MainActivity.SCORE_VALUE, 0)
        binding.highScoreTextView.text = "High Score: $score"


        binding.startButton.setOnClickListener{
            navigator().openGameScreen(viewModel.generateApiRequest())
        }
        setupRecyclerView()
        viewModel.themesList.observe(viewLifecycleOwner){
            themesAdapter.themeList = it
        }
        themesAdapter.onItemClickListener = {
            viewModel.changeItem(it)
        }
        binding.errorButton.setOnClickListener {
            viewModel.getList()
        }
        binding.unselectTextView.setOnClickListener {
            viewModel.unselectAll()
        }
        viewModel.error.observe(viewLifecycleOwner){
            if(it){
                binding.themesRecyclerView.visibility = View.INVISIBLE
                binding.errorButton.visibility = View.VISIBLE

            }
            else{
                binding.themesRecyclerView.visibility = View.VISIBLE
                binding.errorButton.visibility = View.INVISIBLE
            }
        }
    }

    private fun setupRecyclerView(){
        themesAdapter = ThemesAdapter()
        binding.themesRecyclerView.adapter = themesAdapter
    }


}