package com.khaledamin.moviesapplication.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [MovieEntity::class], version = 1)
abstract class MoviesDB : RoomDatabase() {

    abstract fun moviesDao(): MoviesDao

    companion object {
        @Volatile
        private var INSTANCE: MoviesDB? = null
        fun getDatabase(context: Context): MoviesDB {
            val db = INSTANCE
            if (db != null) {
                return db
            }
                synchronized(this) {
                    val instance = Room.databaseBuilder(
                        context.applicationContext,
                        MoviesDB::class.java,
                        "movies_db"
                    ).fallbackToDestructiveMigration()
                        .build()
                    INSTANCE = instance
                    return instance
                }
        }
    }
}