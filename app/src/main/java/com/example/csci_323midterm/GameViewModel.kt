package com.example.csci_323midterm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class GameViewModel(val dao: HighscoreDao) : ViewModel()
{
    /**
     * @param _lastScore
     *      stores the value of the last achieved high score
     * @param lastScore
     *      accessor value for getting _lastScore as a non-mutable livedata object
     */
    val _lastScore = MutableLiveData<String>()
    val lastScore : LiveData<String> get() = _lastScore

    /**
     * @param currentSecretNumber
     *      variable that stores the hidden number the player guesses
     * @param _numberGuesses
     *      stores the number of guesses the player has made thus far
     * @param numberGuesses
     *      accessor value for getting _numberGuesses as a non-mutable livedata object
     * @param _currentGuess
     *      stores the player's current guess for the secret number
     * @param numberGuesses
     *      accessor value for getting _currentGuess as a non-mutable livedata object
     */
    var currentSecretNumber = -1

    val _numberGuesses = MutableLiveData<Int>()
    val numberGuesses : LiveData<Int> get() = _numberGuesses

    val _currentGuess = MutableLiveData<Int?>()
    val currentGuess : LiveData<Int?> get() = _currentGuess

    fun setLastScore(score : String)
    {
        if (score != "null")
        {
            _lastScore.value = score
        }
    }

    fun getLastScore() : String?
    {
        return lastScore.value
    }
}