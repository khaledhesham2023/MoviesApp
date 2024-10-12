package com.khaledamin.moviesapplication.domain.repository

import com.khaledamin.moviesapplication.data.local.MovieEntity
import com.khaledamin.moviesapplication.data.remote.dto.MovieDTO
import com.khaledamin.moviesapplication.domain.model.Movie

interface MoviesRepo {
//    suspend fun getPopularMovies(
//        page: Int,
//        sortBy: String,
//        fetchFromRemote: Boolean = true,
//    ): Flow<State<ArrayList<Movie>>>

    suspend fun setMovieFavoriteOrNot(
        id: Long,
        isFavorite: Boolean,
    )

    suspend fun getMoviesFromRemoteSource(page: Int, sortBy: String): ArrayList<MovieDTO>

    suspend fun getMoviesFromLocalCache(sortBy: String): ArrayList<MovieEntity>

    suspend fun getFavorites() : ArrayList<Movie>

}