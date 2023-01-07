package com.example.quizapi


import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.quizapi.data.ApiInterface
import com.example.quizapi.model.QuestionRepository
import com.example.quizapi.model.Round
import com.example.quizapi.model.Session
import com.example.quizapi.model.SessionUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import retrofit2.Retrofit

class MainViewModel(
    private val sessionUseCase:SessionUseCase,
    private val retrofit: ApiInterface
): ViewModel() {

    private val _round = MutableLiveData<Round>()
    private val _session = MutableLiveData<Session>()

    val round: LiveData<Round> = _round
    val session: LiveData<Session> = _session


    init{
        startSession()
    }
    fun checkAnswer(buttonId: Int){
        if (round.value!!.answers[buttonId]== round.value!!.correct){
            _session.value = sessionUseCase.increaseScore()
        }
        else{
            _session.value = sessionUseCase.decreaseLive()
        }
        loadRound()
    }


    private fun loadRound() {
        viewModelScope.launch() {
                val response = retrofit.getQuestionList()
                if (response.isSuccessful) {
                    val round = response.body()!!.get(0)
                    round.answers += round.correct
                    round.answers.shuffled()
                    _round.value = round
                } else {
                    Log.e("Error","FURT")
                }

    }

    }
    private fun startSession(){
        _session.value = sessionUseCase.createSession()
        loadRound()
    }


}