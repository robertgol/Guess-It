package com.example.android.guesstheword.screens.score

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class ScoreViewModelFactory(private val finalScore: Int) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        require(modelClass.isAssignableFrom(ScoreViewModel::class.java)) {
            "Unknown ViewModel class: $modelClass"
        }
        return ScoreViewModel(finalScore) as T
    }
}