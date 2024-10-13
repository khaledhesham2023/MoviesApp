package com.khaledamin.moviesapplication.presentation.callbacks

import com.khaledamin.moviesapplication.domain.model.Movie

interface MovieCallback {
    fun onMovieClicked(movie: Movie)
}