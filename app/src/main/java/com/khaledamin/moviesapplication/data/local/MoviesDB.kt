package com.khaledamin.moviesapplication.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [MovieEntity::class], version = 1)
abstract class MoviesDB : RoomDatabase() {
    abstract fun moviesDao(): MoviesDao
}