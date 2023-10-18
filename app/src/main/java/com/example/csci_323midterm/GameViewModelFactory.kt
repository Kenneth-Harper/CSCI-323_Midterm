package com.example.csci_323midterm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class GameViewModelFactory()
    : ViewModelProvider.Factory
{
    override fun <T : ViewModel> create(modelClass: Class<T>): T
    {
        if (modelClass.isAssignableFrom(GameViewModel::class.java))
        {
            return GameViewModel() as T
        }
        throw IllegalArgumentException("Unknown ViewModel")
    }
}