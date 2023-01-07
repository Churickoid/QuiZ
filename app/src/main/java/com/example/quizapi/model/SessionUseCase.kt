package com.example.quizapi.model

class SessionUseCase {
    lateinit var currentSession: Session

    fun createSession():Session{
        currentSession = Session(3,0)
        return takeCurrentSession()
    }
    fun decreaseLive():Session{
        currentSession.lives--
        return takeCurrentSession()
    }
    fun increaseScore():Session{
        currentSession.score++
        return takeCurrentSession()
    }
    private fun takeCurrentSession():Session{
        return currentSession
    }
}