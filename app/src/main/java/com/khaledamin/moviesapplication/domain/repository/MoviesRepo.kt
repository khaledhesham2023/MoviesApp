package com.khaledamin.moviesapplication.domain.repository

import com.khaledamin.moviesapplication.data.local.MovieEntity
import com.khaledamin.moviesapplication.data.remote.dto.MovieDTO
import com.khaledamin.moviesapplication.domain.model.Movie

interface MoviesRepo {

    suspend fun setFavoriteState(
        id: Long,
        isFavorite: Boolean,
    ) : Int

    suspend fun getMoviesFromRemote(page: Int, sortBy: String): ArrayList<MovieDTO>

    suspend fun getMoviesFromDatabase(sortBy: String): ArrayList<MovieEntity>

    suspend fun getFavoritesList() : ArrayList<Movie>

    fun checkConnection() : Boolean

}