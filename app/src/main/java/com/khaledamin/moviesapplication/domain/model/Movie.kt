package com.khaledamin.moviesapplication.domain.model

import java.io.Serializable

data class Movie(
    val id: Long?,
    val adult: Boolean?,
    val backdropPath: String?,
    val originalLanguage: String?,
    val originalTitle: String?,
    val overview: String?,
    val popularity: Double?,
    val posterPath: String?,
    val releaseDate: String?,
    val title: String?,
    val voteAverage: Double?,
    val voteCount: Int?,
    var isFavorite: Boolean = false,
) : Serializable