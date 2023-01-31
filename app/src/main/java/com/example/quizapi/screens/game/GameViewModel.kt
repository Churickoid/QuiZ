package com.example.quizapi.screens.game



import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope

import com.example.quizapi.model.QuestionRepository
import com.example.quizapi.model.Round

import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class GameViewModel(
    private val repository: QuestionRepository
): ViewModel() {


    private val _round = MutableLiveData<Round>()
    val round: LiveData<Round> = _round

    private val _lives = MutableLiveData<Int>()
    private val _score = MutableLiveData<Int>()
    private val _timer = MutableLiveData<Int>()

    val lives: LiveData<Int> = _lives
    val score: LiveData<Int> = _score
    val timer: LiveData<Int> = _timer

    private val _panel = MutableLiveData(true)
    val panel: LiveData<Boolean> = _panel

    private val _error = MutableLiveData(false)
    val error: LiveData<Boolean> = _error

    private val _buttonId = MutableLiveData<Int>()
    val buttonId: LiveData<Int> = _buttonId

    private val _end= MutableLiveData<Boolean>()
    val end: LiveData<Boolean> = _end

    private val _disabled= MutableLiveData<Boolean>()
    val disabled: LiveData<Boolean> = _disabled

    init{
        startSession()
        startTimer()
    }
    fun checkAnswer(buttonId: Int){
        if (getCorrectAnswer()== getCurrentAnswer(buttonId)){
            _score.value = _score.value!! + 1
            _buttonId.value = buttonId
        }
        else {
            _lives.value = _lives.value!! - 1
            _buttonId.value = buttonId+4
        }
        viewModelScope.launch(){
            _timer.value = -1
            _disabled.value = true
            delay(500)
            _disabled.value = false
            _buttonId.value = buttonId+8
            if (_lives.value == 0) _end.value = true
            else loadRound()
        }

    }


    fun loadRound() {
        viewModelScope.launch{
            _panel.value = false
            _error.value = false
            try {
                _round.value = repository.getQuestion()
                _panel.value = true
                _timer.value = 30
            }
            catch(e: Exception){
                _error.value = true
            }
    }

    }
    private fun startSession(){
        _score.value = 0
        _lives.value = 3
        loadRound()
    }

    private fun getCorrectAnswer():String{
        return round.value!!.correct
    }
    private fun getCurrentAnswer(buttonId: Int):String{
        if (buttonId == 4) return  ""
        return round.value!!.answers[buttonId]
    }
    private fun startTimer(){
        viewModelScope.launch() {
            _timer.value = -1
            tick()
        }
    }
    private suspend fun tick(){
        _timer.postValue(_timer.value!! - 1)
        if (_timer.value == 0){
            checkAnswer(4)
        }
        delay(1000)
        tick()
    }

}