package com.khaledamin.moviesapplication.domain.usecases

import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.khaledamin.moviesapplication.domain.model.Movie
import com.khaledamin.moviesapplication.domain.paging.MoviesPagingSource
import com.khaledamin.moviesapplication.domain.repository.MoviesRepo
import javax.inject.Inject

class GetMoviesByPagingUseCases @Inject constructor(
    private val repo: MoviesRepo,
) {

    fun invoke(sortBy: String, fetchFromRemote: Boolean): Pager<Int, Movie> {
        return Pager(
            config = PagingConfig(
                pageSize = 20,
                maxSize = 60
            ),
            pagingSourceFactory = { MoviesPagingSource(repo, sortBy, fetchFromRemote) }
        )
    }
}