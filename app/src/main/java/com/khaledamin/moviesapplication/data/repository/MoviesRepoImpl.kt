package com.khaledamin.moviesapplication.data.repository

import com.khaledamin.moviesapplication.data.local.MovieEntity
import com.khaledamin.moviesapplication.data.local.MoviesDao
import com.khaledamin.moviesapplication.data.remote.MoviesApi
import com.khaledamin.moviesapplication.data.remote.dto.MovieDTO
import com.khaledamin.moviesapplication.data.toMovie
import com.khaledamin.moviesapplication.data.toMovieEntity
import com.khaledamin.moviesapplication.domain.model.Movie
import com.khaledamin.moviesapplication.domain.repository.MoviesRepo
import javax.inject.Inject

class MoviesRepoImpl @Inject constructor(
    private val api: MoviesApi,
    private val dao: MoviesDao,
) : MoviesRepo {


    override suspend fun setMovieFavoriteOrNot(id: Long, isFavorite: Boolean) {
        dao.setFavoriteOrNot(id, isFavorite)
    }

    override suspend fun getMoviesFromRemoteSource(page: Int, sortBy: String): ArrayList<MovieDTO> {
        val movies = api.getMovies(page = page, sortBy = sortBy).results!!
        val localMoviesMap = dao.getMovies(sortBy).associateBy { it.id }
        dao.insertAll(movies.map {
            val existingMovie = localMoviesMap[it.id]
            it.toMovieEntity(existingMovie) }.toCollection(ArrayList()))
        return movies
    }

    override suspend fun getMoviesFromLocalCache(sortBy: String): ArrayList<MovieEntity> {
        val localMovies = dao.getMovies(sortBy).toCollection(ArrayList())
        return localMovies
    }

    override suspend fun getFavorites(): ArrayList<Movie> {
        val localMovies = dao.getFavorites()
        val localMoviesMap = dao.getFavorites().associateBy { it.id }
        return localMovies.map {
            val existingMovie = localMoviesMap[it.id]
            it.toMovie(existingMovie) }.toCollection(ArrayList())
    }
}