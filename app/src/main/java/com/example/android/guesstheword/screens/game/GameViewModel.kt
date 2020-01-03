package com.example.android.guesstheword.screens.game

import android.os.CountDownTimer
import android.text.format.DateUtils
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel

class GameViewModel : ViewModel() {
    companion object {
        // These represent different important times
        // This is when the game is over
        const val DONE = 0L
        // This is the number of milliseconds in a second
        const val ONE_SECOND = 1000L
        // This is the total time of the game
        const val COUNTDOWN_TIME = 6000L
    }

    override fun onCleared() {
        super.onCleared()
        timer.cancel()
    }

    private lateinit var timer: CountDownTimer

    private val _wordData = MutableLiveData<String>()
    val wordData: LiveData<String>
        get() = _wordData

    private val _scoreData = MutableLiveData<Int>()
    val scoreData: LiveData<Int>
        get() = _scoreData

    private val _eventGameFinish = MutableLiveData<Boolean>()
    val eventGameFinish: LiveData<Boolean>
        get() = _eventGameFinish

    private val secondsUntilFinishedData = MutableLiveData<Long>()

    val secondUntilFinishedStringData = Transformations.map(secondsUntilFinishedData) {
        DateUtils.formatElapsedTime(it)
    }

    private val _eventBuzz = MutableLiveData(BuzzType.NO_BUZZ)
    val eventBuzz: LiveData<BuzzType>
        get() = _eventBuzz

    // The list of words - the front of the list is the next word to guess
    private lateinit var wordList: MutableList<String>

    init {
        resetList()
        nextWord()
        _scoreData.value = 0
        _eventGameFinish.value = false
        setupTimer()
        timer.start()
    }

    private fun setupTimer() {
        timer = object : CountDownTimer(COUNTDOWN_TIME, ONE_SECOND) {
            override fun onFinish() {
                _eventBuzz.value = BuzzType.GAME_OVER
                _eventGameFinish.value = true
            }

            override fun onTick(millisUntilFinished: Long) {
                secondsUntilFinishedData.value = millisUntilFinished / 1000
                if (millisUntilFinished / 1000 < 5) {
                    _eventBuzz.value = BuzzType.COUNTDOWN_PANIC
                }
            }
        }
    }

    /** Resets the list of words and randomizes the order */
    private fun resetList() {
        wordList = mutableListOf(
                "queen",
                "hospital",
                "basketball",
                "cat",
                "change",
                "snail",
                "soup",
                "calendar",
                "sad",
                "desk",
                "guitar",
                "home",
                "railway",
                "zebra",
                "jelly",
                "car",
                "crow",
                "trade",
                "bag",
                "roll",
                "bubble"
        )
        wordList.shuffle()
    }

    private fun nextWord() {
        if (wordList.isEmpty()) {
            resetList()
        }
        _wordData.value = wordList.removeAt(0)
    }

    fun onSkip() {
        _scoreData.value = (scoreData.value)?.minus(1)
        nextWord()
    }

    fun onCorrect() {
        _eventBuzz.value = BuzzType.CORRECT
        _scoreData.value = (scoreData.value)?.plus(1)
        nextWord()
    }

    fun onGameFinishComplete() {
        _eventGameFinish.value = false
    }

    fun onBuzzComplete() {
        _eventBuzz.value = BuzzType.NO_BUZZ
    }
}
