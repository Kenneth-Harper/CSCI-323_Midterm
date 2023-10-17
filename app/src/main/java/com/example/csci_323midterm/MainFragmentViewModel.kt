package com.example.csci_323midterm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainFragmentViewModel : ViewModel()
{
    val _lastScore = MutableLiveData<String>()
    val lastScore : LiveData<String> get() = _lastScore

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