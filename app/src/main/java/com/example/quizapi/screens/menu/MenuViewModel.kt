package com.example.quizapi.screens.menu

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.quizapi.model.Theme
import com.example.quizapi.model.repository.ThemesRepository
import kotlinx.coroutines.launch

class MenuViewModel(private val themesRepository: ThemesRepository) : ViewModel() {

    private val _error = MutableLiveData(false)
    val error: LiveData<Boolean> = _error

    private val _themesList = MutableLiveData<List<Theme>>()
    val themesList:LiveData<List<Theme>> = _themesList
    init{
       getList()
    }


    fun generateApiRequest():String{
        if(themesList.value == null) return ""
        var request = ""
        for (i in themesList.value!!){
            if (i.isActive){
            request += i.themeRequest
            request += ","
            }
        }
        return request
    }
    fun changeItem(theme:Theme){
        _themesList.value = themesRepository.changeActiveById(theme.id)
    }

    fun unselectAll(){
        _themesList.value =  themesRepository.unselectAllActive()
    }

    fun getList(){
        viewModelScope.launch{
            _error.value = false
            try {
                _themesList.value = themesRepository.getThemes()
            }
            catch (e:Exception){
                _error.value = true
            }

        }

    }
}