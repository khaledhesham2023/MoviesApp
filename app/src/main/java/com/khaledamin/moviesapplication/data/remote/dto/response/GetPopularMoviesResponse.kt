package com.khaledamin.moviesapplication.data.remote.dto.response

import com.google.gson.annotations.SerializedName
import com.khaledamin.moviesapplication.data.remote.dto.MovieDTO

data class GetPopularMoviesResponse(
    val page: Int?,
    val results: ArrayList<MovieDTO>?,
    @SerializedName("total_pages")
    val totalPages: Int?,
    @SerializedName("total_results")
    val totalResults: Int?,
)