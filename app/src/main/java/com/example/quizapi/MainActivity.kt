package com.example.quizapi

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.example.quizapi.databinding.ActivityMainBinding
import com.example.quizapi.navigation.Navigator
import com.example.quizapi.navigation.StringTools
import com.example.quizapi.screens.EndFragment
import com.example.quizapi.screens.game.GameFragment
import com.example.quizapi.screens.menu.MenuFragment


class MainActivity : AppCompatActivity(), Navigator, StringTools {

    private lateinit var binding: ActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        if (savedInstanceState == null) {
            supportFragmentManager
                .beginTransaction()
                .add(R.id.fragmentContainer, MenuFragment())
                .commit()
        }

    }
    companion object{
        const val APP_PREFERENCE = "APP_PREFERENCE"
        const val SCORE_VALUE = "SCORE_VALUE"
    }

    override fun openEndScreen(currentScore: Int) {
        launchFragment(EndFragment().newInstance(currentScore))
    }

    override fun openGameScreen(requestApi: String) {
        launchFragment(GameFragment().newInstance(requestApi))
    }

    override fun goBack() {
        onBackPressed()
    }

    override fun goToMenu() {
        supportFragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)
    }

    private fun launchFragment(fragment: Fragment) {
        supportFragmentManager
            .beginTransaction()
            .addToBackStack(null)
            .replace(R.id.fragmentContainer, fragment)
            .commit()
    }

    override fun changeTitle(title: String) {
        supportActionBar?.title = title
    }


}