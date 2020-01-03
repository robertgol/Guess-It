package com.example.android.guesstheword.screens.score

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ScoreViewModel(finalScore: Int) : ViewModel() {
    private val _finalScoreData = MutableLiveData(finalScore)
    val finalScoreData: LiveData<Int>
        get() = _finalScoreData

    private val _eventPlayAgain = MutableLiveData(false)
    val eventPlayAgain: LiveData<Boolean>
        get() = _eventPlayAgain

    fun onPlayAgain() {
        _eventPlayAgain.value = true
    }

    fun onPlayAgainComplete() {
        _eventPlayAgain.value = false
    }
}