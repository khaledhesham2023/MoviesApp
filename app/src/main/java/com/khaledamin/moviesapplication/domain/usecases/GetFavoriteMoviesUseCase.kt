package com.khaledamin.moviesapplication.domain.usecases

import com.khaledamin.moviesapplication.domain.model.Movie
import com.khaledamin.moviesapplication.domain.repository.MoviesRepo
import javax.inject.Inject

class GetFavoriteMoviesUseCase @Inject constructor(
    private val repo: MoviesRepo,
) {

    suspend fun invoke(): ArrayList<Movie> {
        return repo.getFavorites()
    }
}