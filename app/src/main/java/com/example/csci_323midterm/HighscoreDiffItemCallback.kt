package com.example.csci_323midterm

import androidx.recyclerview.widget.DiffUtil

class HighscoreDiffItemCallback : DiffUtil.ItemCallback<Highscore>()
{
    override fun areItemsTheSame(oldItem: Highscore, newItem: Highscore) = (oldItem.scoreId == newItem.scoreId)
    override fun areContentsTheSame(oldItem: Highscore, newItem: Highscore) = (oldItem == newItem)
}