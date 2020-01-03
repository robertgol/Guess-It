package com.example.android.guesstheword.screens.game

import androidx.lifecycle.ViewModel
import timber.log.Timber

class GameViewModel : ViewModel() {
    init {
        Timber.i("Initialized")
    }

    override fun onCleared() {
        super.onCleared()
        Timber.i("onCleared")
    }
}
