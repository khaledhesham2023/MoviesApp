package com.khaledamin.moviesapplication.presentation.callbacks

import com.khaledamin.moviesapplication.domain.model.Movie

interface MovieFavoriteButtonCallback {
    fun onFavoriteButtonClicked(movie: Movie, isFavorite:Boolean = true)
}