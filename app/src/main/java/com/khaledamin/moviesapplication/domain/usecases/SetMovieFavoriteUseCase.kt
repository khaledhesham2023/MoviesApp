package com.khaledamin.moviesapplication.domain.usecases

import com.khaledamin.moviesapplication.domain.repository.MoviesRepo
import javax.inject.Inject

class SetMovieFavoriteUseCase @Inject constructor(
    private val moviesRepo: MoviesRepo,
) {
    suspend fun invoke(id: Long, isFavorite: Boolean) =
        moviesRepo.setMovieFavoriteOrNot(id, isFavorite)
}