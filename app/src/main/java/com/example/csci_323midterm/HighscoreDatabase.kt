package com.example.csci_323midterm

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Highscore::class], version = 1)
abstract class HighscoreDatabase : RoomDatabase() {
    abstract val highscoreDao: HighscoreDao

    /**
     * Establishes the Instance of the database object in a companion object, which works akin to the usual
     * singleton pattern seen in other languages like Java or C#, but just done a little weird in Kotlin
     */
    companion object {
        @Volatile
        private var INSTANCE: HighscoreDatabase? = null
        fun getInstance(context: Context): HighscoreDatabase {
            synchronized(this) {
                var instance = INSTANCE
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        HighscoreDatabase::class.java,
                        "notes_database"
                    ).build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}