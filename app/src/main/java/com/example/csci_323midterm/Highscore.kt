package com.example.csci_323midterm

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "highscore_table")
data class Highscore(
    @PrimaryKey(autoGenerate = true)
    var scoreId: Long = 0L,
    @ColumnInfo(name = "player_name")
    var playerName: String="",
    @ColumnInfo(name = "number_guesses")
    var numberGuesses: Int=0,
    @ColumnInfo(name = "score_display")
    var scoreDisplay: String="$playerName score: $numberGuesses")