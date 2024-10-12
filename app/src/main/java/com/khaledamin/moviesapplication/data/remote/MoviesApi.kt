package com.khaledamin.moviesapplication.data.remote

import com.khaledamin.moviesapplication.data.remote.dto.response.GetPopularMoviesResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface MoviesApi {
    @GET("discover/movie")
    suspend fun getMovies(
        @Query("include_adult") adult: Boolean = false,
        @Query("language") language: String = "en-US",
        @Query("sort_by") sortBy: String,
        @Query("vote_count.gte") voteCount: Int = 200,
        @Query("page") page: Int?,
    ): GetPopularMoviesResponse
}