package com.example.csci_323midterm

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface HighscoreDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(highscore: Highscore)
    @Update
    suspend fun update(highscore: Highscore)
    @Delete
    suspend fun delete(highscore: Highscore)
    @Query("SELECT * FROM highscore_table WHERE scoreId LIKE :key")
    fun get(key: Long): LiveData<Highscore>
    @Query("SELECT * FROM highscore_table ORDER BY number_guesses ASC")
    fun getAll(): LiveData<List<Highscore>>
}