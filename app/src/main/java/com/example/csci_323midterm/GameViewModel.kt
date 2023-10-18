package com.example.csci_323midterm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class GameViewModel : ViewModel()
{
    /**
     * @param _lastScore
     *      stores the value of the last achieved high score
     * @param lastScore
     *      accessor value for getting _lastScore as a non-mutable livedata object
     */
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
     */
    private var currentSecretNumber = -1
    var playerName = ""

    /**
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
    val _numberGuesses = MutableLiveData<Int>()
    val numberGuesses : LiveData<Int> get() = _numberGuesses

    val _currentGuess = MutableLiveData<Int>()
    val currentGuess : LiveData<Int> get() = _currentGuess

    val _guessLessThanSecret = MutableLiveData<Boolean?>()
    val guessLessThanSecret : LiveData<Boolean?> get() = _guessLessThanSecret

    /**
     * @param _navigateToHome
     *      stores whether or not the app should navigate to the main fragment
     * @param navigateToHome
     *      accessor value for getting _navigateToHome as a non-mutable livedata object
     */
    val _navigateToHome = MutableLiveData<Boolean>()
    val navigateToHome : LiveData<Boolean> get() = _navigateToHome

    /**
     * <h1> setDao </h1>
     * Sets the dao value for the shared view model
     * @param d : HighscoreDao
     *      the HighscoreDao that the view model's dao will be set to
     */
    fun setDao(d : HighscoreDao)
    {
        dao = d
    }

    /**
     * <h1> setLastScore </h1>
     * Sets the lastScore value for the shared view model
     * @param score : String
     *      The value for the most recently achieved score
     */
    fun setLastScore(score : String)
    {
        if (score != "null")
        {
            _lastScore.value = score
        }
    }

    /**
     * <h1> clearLastScore </h1>
     * Sets the lastScore value to "null"
     */
    fun clearLastScore()
    {
        _lastScore.value = "null"
    }

    /**
     * <h1> getScores </h1>
     * Sets the scores variable to the entries retrieved from the database
     */
    fun getScores()
    {
        scores = dao.getAll()
    }

    /**
     * <h1> createSecretNumber </h1>
     * Resets the game variables and randomly selects a number for currentSecretNumber
     */
    fun createSecretNumber()
    {
        _navigateToHome.postValue(false)
        _numberGuesses.postValue(0)
        _currentGuess.postValue(-1)
        currentSecretNumber = (1 until 101).random()
    }

    /**
     * <h1> setCurrentGuess </h1>
     * Sets the currentGuess value to the most recently input player guess
     * @param guess : Int
     *      The value of the player guess
     */
    fun setCurrentGuess(guess : Int) { _currentGuess.value = guess }

    /**
     * <h1> increaseGuess </h1>
     * Increases the currentGuess value by 1, so long as currentGuess has been initialized
     */
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

    /**
     * <h1> decreaseGuess </h1>
     * Decreases the currentGuess value by 1, so long as currentGuess has been initialized
     */
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

    /**
     * <h1> submitGuess </h1>
     * Submits the player's currentGuess and evaluates the proximity to the secretNumber
     */
    fun submitGuess()
    {
        _guessLessThanSecret.postValue(null)
        if (currentGuess.value != -1)
        {
            _numberGuesses.postValue(_numberGuesses.value?.plus(1))

            if (currentGuess.value!! == currentSecretNumber)
            {
                val newScore = Highscore()
                newScore.playerName = if (playerName == "") "UnnamedPlayer" else playerName
                newScore.numberGuesses = numberGuesses.value!!
                newScore.scoreDisplay = "${newScore.playerName} score: ${numberGuesses.value!! + 1}"
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

    /**
     * <h1> deleteScore </h1>
     * Deletes the specified highscore from the database
     * @param scoreId : Long
     *      The id of the highscore which is to be deleted
     */
    fun deleteScore(scoreId : Long)
    {
        val score = Highscore()
        score.scoreId = scoreId
        viewModelScope.launch {
            dao.delete(score)
        }
    }
}