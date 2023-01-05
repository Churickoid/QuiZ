package com.example.quizapi


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.quizapi.model.QuestionRepository
import com.example.quizapi.model.Round
import com.example.quizapi.model.Session

class MainViewModel(
    private val questionRepository: QuestionRepository
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
            _session.value!!.score++
        }
        else{
            _session.value!!.lives--
        }
        _session.value = _session.value
        loadRound()
    }


    private fun loadRound() {
        _round.value = questionRepository.getQuestion()
    }
    private fun startSession(){
        _session.value = Session(3,0)
        loadRound()
    }


}