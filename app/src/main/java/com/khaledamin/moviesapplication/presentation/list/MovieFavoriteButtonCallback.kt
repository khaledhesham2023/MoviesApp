package com.khaledamin.moviesapplication.presentation.list

import com.khaledamin.moviesapplication.domain.model.Movie

interface MovieFavoriteButtonCallback {
    fun onFavoriteButtonClicked(movie: Movie, isFavorite:Boolean = true)
}