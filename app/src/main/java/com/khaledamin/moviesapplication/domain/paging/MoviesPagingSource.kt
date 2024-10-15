package com.khaledamin.moviesapplication.domain.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.khaledamin.moviesapplication.data.toMovie
import com.khaledamin.moviesapplication.domain.model.Movie
import com.khaledamin.moviesapplication.domain.repository.MoviesRepo

class MoviesPagingSource(
    private val repo: MoviesRepo,
    private val sortBy: String,
    private val fetchFromRemote: Boolean,
) : PagingSource<Int, Movie>() {
    override fun getRefreshKey(state: PagingState<Int, Movie>): Int? {
        return state.anchorPosition.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition!!)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Movie> {
        return try {
            val page = params.key ?: 1
            val response = if (fetchFromRemote) repo.getMoviesFromRemote(page, sortBy)
                .map { it ->
                    val existingMovie =
                        repo.getMoviesFromDatabase(sortBy).associateBy { it.id }[it.id]
                    it.toMovie(existingMovie)
                } else {
                repo.getMoviesFromDatabase(sortBy).map { it.toMovie() }
            }
            LoadResult.Page(
                data = response,
                prevKey = if (page == 1) null else page - 1,
                nextKey = if (response.isEmpty()) null else page + 1
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}