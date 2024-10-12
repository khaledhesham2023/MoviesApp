package com.khaledamin.moviesapplication.data

import com.khaledamin.moviesapplication.data.remote.dto.MovieDTO
import com.khaledamin.moviesapplication.domain.model.Movie
import com.khaledamin.moviesapplication.data.local.MovieEntity

fun MovieDTO.toMovieEntity(existingEntity: MovieEntity?): MovieEntity {
    return MovieEntity(
        id = id,
        adult = adult,
        backdropPath = backdropPath,
        originalLanguage = originalLanguage,
        originalTitle = originalTitle,
        overview = overview,
        popularity = popularity,
        posterPath = posterPath,
        releaseDate = releaseDate,
        title = title,
        voteAverage = voteAverage,
        voteCount = voteCount,
        isFavorite = existingEntity?.isFavorite ?: false
    )
}

fun MovieEntity.toMovie(existingMovie:MovieEntity? = null): Movie {
    return Movie(
        id = id,
        adult = adult,
        backdropPath = backdropPath,
        originalLanguage = originalLanguage,
        originalTitle = originalTitle,
        overview = overview,
        popularity = popularity,
        posterPath = posterPath,
        releaseDate = releaseDate,
        title = title,
        voteAverage = voteAverage,
        voteCount = voteCount,
        isFavorite = existingMovie?.isFavorite ?: false
    )
}
fun MovieDTO.toMovie(existingMovie: MovieEntity?): Movie {
    return Movie(
        id = id,
        adult = adult,
        backdropPath = backdropPath,
        originalLanguage = originalLanguage,
        originalTitle = originalTitle,
        overview = overview,
        popularity = popularity,
        posterPath = posterPath,
        releaseDate = releaseDate,
        title = title,
        voteAverage = voteAverage,
        voteCount = voteCount,
        isFavorite = existingMovie!!.isFavorite
    )
}