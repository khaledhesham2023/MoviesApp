package com.khaledamin.moviesapplication.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface MoviesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(movies: ArrayList<MovieEntity>)

    @Query("""
    SELECT * FROM movies 
    ORDER BY CASE 
        WHEN :sortBy = 'popularity.desc' THEN popularity 
        WHEN :sortBy = 'vote_average.desc' THEN voteAverage 
    END DESC
""")
    suspend fun getMovies(sortBy: String): List<MovieEntity>

    @Query(value = "UPDATE movies SET isFavorite = :isFavorite WHERE id = :id")
    suspend fun setFavoriteState(id: Long, isFavorite: Boolean): Int

    @Query(value = "SELECT * FROM movies WHERE isFavorite = 1")
    suspend fun getFavoritesList(): List<MovieEntity>
}