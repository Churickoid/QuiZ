package com.example.quizapi.screens.game



import android.os.Bundle
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope

import com.example.quizapi.model.repository.QuestionRepository
import com.example.quizapi.model.Round

import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class GameViewModel(
    private val repository: QuestionRepository
): ViewModel() {

    private val _round = MutableLiveData<Round>()
    val round: LiveData<Round> = _round

    private val _numRound = MutableLiveData<Int>()
    val numRound : LiveData<Int> = _numRound

    private var resetScreen = true
    private var requestApi:String = ""

    private val _lives = MutableLiveData<Int>()
    private val _score = MutableLiveData<Int>()
    private val _timer = MutableLiveData<Int>()

    val lives: LiveData<Int> = _lives
    val score: LiveData<Int> = _score
    val timer: LiveData<Int> = _timer

    private val _panel = MutableLiveData(1)
    val panel: LiveData<Int> = _panel

    private val _buttonId = MutableLiveData<Int>()
    val buttonId: LiveData<Int> = _buttonId

    private val _end= MutableLiveData<Int>()
    val end: LiveData<Int> = _end

    private val _disableButtons= MutableLiveData<Boolean>()
    val disableButtons: LiveData<Boolean> = _disableButtons
    private var oneButtonClickedChecker = true

    init {
        startTimer()
    }
    fun checkAnswer(buttonId: Int){
        if (!oneButtonClickedChecker) return
        oneButtonClickedChecker= false
        _disableButtons.value = true
        if (getCorrectAnswer()== getCurrentAnswer(buttonId)){
            _score.value = _score.value!! + 1
            _buttonId.value = buttonId
        }
        else {
            _lives.value = _lives.value!! - 1
            _buttonId.value = buttonId+4
        }

        viewModelScope.launch(){
            _timer.value = OFF_TIMER_VALUE
            delay(600)
            _buttonId.value = buttonId+8
            _disableButtons.value = false
            if (_lives.value == 0) {
                _end.value = _score.value
                resetScreen = true
            }

            else loadRound()
        }

    }

    fun setApiAndLoad(request:String){
        requestApi = request
        loadRound()
    }
     fun loadRound() {
        viewModelScope.launch{
            _panel.value = PROGRESSBAR_PANEL
            try {
                oneButtonClickedChecker= true
                _round.value = repository.getQuestion(requestApi)
                _timer.value = DEFAULT_TIMER_VALUE
                _panel.value = BUTTONS_PANEL
                _numRound.value = _numRound.value!! + 1
            }
            catch(e: Exception){
                _panel.value = ERROR_PANEL
            }
        }

    }
    fun getResetValue():Boolean{
        return resetScreen
    }
    fun startSession(){
        _score.value = DEFAULT_SCORE_VALUE
        _lives.value = DEFAULT_LIVES_VALUE
        _numRound.value = 0
        _end.value = -1
        resetScreen  = false

    }

    private fun getCorrectAnswer():String{
        return round.value!!.correct
    }
    private fun getCurrentAnswer(buttonId: Int):String{
        if (buttonId == 4) return  ""
        return round.value!!.answers[buttonId]
    }
    private fun startTimer(){
        _timer.value = DEFAULT_TIMER_VALUE
        viewModelScope.launch() {
            tick()
        }
    }
    private suspend fun tick(){
        if (_timer.value == END_TIMER_VALUE){
            checkAnswer(4)
        }
        delay(1000)
        _timer.postValue(_timer.value!! - 1)
        tick()
    }
    companion object{
        private const val DEFAULT_LIVES_VALUE = 3
        private const val DEFAULT_SCORE_VALUE = 0
        private const val DEFAULT_TIMER_VALUE = 30

        private const val END_TIMER_VALUE = 0
        private const val OFF_TIMER_VALUE = -1

        private const val PROGRESSBAR_PANEL = 0
        private const val BUTTONS_PANEL = 1
        private const val ERROR_PANEL = 2
    }

}