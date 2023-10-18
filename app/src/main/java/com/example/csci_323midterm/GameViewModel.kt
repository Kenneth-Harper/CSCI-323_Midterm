package com.example.csci_323midterm

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class GameViewModel : ViewModel()
{

    private lateinit var dao : HighscoreDao
    lateinit var scores : LiveData<List<Highscore>>
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
     * @param playerName
     *      stores the player's input name for the highscore
     * @param _numberGuesses
     *      stores the number of guesses the player has made thus far
     * @param numberGuesses
     *      accessor value for getting _numberGuesses as a non-mutable livedata object
     * @param _currentGuess
     *      stores the player's current guess for the secret number
     * @param numberGuesses
     *      accessor value for getting _currentGuess as a non-mutable livedata object
     * @param _guessLessThanSecret
     *      stores whether or not the current guess is less than the secret number
     * @param guessLessThanSecret
     *      accessor value for getting _guessLessThanSecret as a non-mutable livedata object
     */
    var currentSecretNumber = -1
    var playerName = ""

    val _numberGuesses = MutableLiveData<Int>()
    val numberGuesses : LiveData<Int> get() = _numberGuesses

    val _currentGuess = MutableLiveData<Int>()
    val currentGuess : LiveData<Int> get() = _currentGuess

    val _guessLessThanSecret = MutableLiveData<Boolean?>()
    val guessLessThanSecret : LiveData<Boolean?> get() = _guessLessThanSecret
    val _navigateToHome = MutableLiveData<Boolean>()
    val navigateToHome : LiveData<Boolean> get() = _navigateToHome

    fun setDao(d : HighscoreDao)
    {
        dao = d
    }

    fun setLastScore(score : String)
    {
        if (score != "null")
        {
            _lastScore.value = score
        }
    }

    fun clearLastScore()
    {
        _lastScore.value = "null"
    }

    fun getScores()
    {
        scores = dao.getAll()
    }

    fun createSecretNumber()
    {
        _navigateToHome.postValue(false)
        _numberGuesses.postValue(0)
        _currentGuess.postValue(-1)
        currentSecretNumber = (1 until 101).random()
    }

    fun setCurrentGuess(guess : Int) { _currentGuess.value = guess }

    fun increaseGuess()
    {
        if (currentGuess.value != -1)
        {
            if (currentGuess.value!! >= 100)
            {
                _currentGuess.postValue(100)
            }
            else
            {
                _currentGuess.value = currentGuess.value?.plus(1)
            }
        }
    }

    fun decreaseGuess()
    {
        if (currentGuess.value != -1)
        {
            if (currentGuess.value!! <= 0)
            {
                _currentGuess.postValue(0)
            }
            else
            {
                _currentGuess.value = currentGuess.value?.minus(1)
            }
        }
    }

    fun submitGuess()
    {
        _guessLessThanSecret.postValue(null)
        if (currentGuess.value != -1)
        {
            _numberGuesses.postValue(_numberGuesses.value?.plus(1))

            if (currentGuess.value!! == currentSecretNumber)
            {
                val newScore = Highscore()
                newScore.playerName = playerName
                newScore.numberGuesses = numberGuesses.value!!
                newScore.scoreDisplay = "$playerName score: ${numberGuesses.value!! + 1}"
                viewModelScope.launch {
                    dao.insert(newScore)
                }
                _navigateToHome.postValue(true)
            }
            else if (currentGuess.value!! < currentSecretNumber)
            {
                _guessLessThanSecret.postValue(true)
            }
            else
            {
                _guessLessThanSecret.postValue(false)
            }
        }
    }

    fun deleteScore(scoreId : Long)
    {
        val score = Highscore()
        score.scoreId = scoreId
        viewModelScope.launch {
            dao.delete(score)
        }
    }
}