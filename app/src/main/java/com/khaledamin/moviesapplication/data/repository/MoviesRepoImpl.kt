package com.khaledamin.moviesapplication.data.repository

import com.khaledamin.moviesapplication.data.local.MovieEntity
import com.khaledamin.moviesapplication.data.local.MoviesDao
import com.khaledamin.moviesapplication.data.remote.MoviesApi
import com.khaledamin.moviesapplication.data.remote.NetworkState
import com.khaledamin.moviesapplication.data.remote.dto.MovieDTO
import com.khaledamin.moviesapplication.data.toMovie
import com.khaledamin.moviesapplication.data.toMovieEntity
import com.khaledamin.moviesapplication.domain.model.Movie
import com.khaledamin.moviesapplication.domain.repository.MoviesRepo
import javax.inject.Inject

class MoviesRepoImpl @Inject constructor(
    private val api: MoviesApi,
    private val dao: MoviesDao,
    private val networkState: NetworkState,
) : MoviesRepo {

    override suspend fun setFavoriteState(id: Long, isFavorite: Boolean): Int {
        return dao.setFavoriteState(id, isFavorite)
    }

    override suspend fun getMoviesFromRemote(page: Int, sortBy: String): ArrayList<MovieDTO> {
        val movies = api.getMovies(page = page, sortBy = sortBy).results
        val localMoviesMap = dao.getMovies(sortBy).associateBy { it.id }
        movies?.map {
            val existingMovie = localMoviesMap[it.id]
            it.toMovieEntity(existingMovie)
        }?.let { dao.insertAll(it.toCollection(ArrayList())) }
        return movies ?: ArrayList()
    }

    override suspend fun getMoviesFromDatabase(sortBy: String): ArrayList<MovieEntity> {
        return dao.getMovies(sortBy).toCollection(ArrayList())
    }

    override suspend fun getFavoritesList(): ArrayList<Movie> {
        return dao.getFavoritesList().map {
            it.toMovie()
        }.toCollection(ArrayList())
    }

    override fun checkConnection(): Boolean {
        return networkState.isInternetAvailable()
    }
}