package com.khaledamin.moviesapplication.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "movies")
data class MovieEntity(
    @PrimaryKey(autoGenerate = false)
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
    val isFavorite: Boolean = false,
)