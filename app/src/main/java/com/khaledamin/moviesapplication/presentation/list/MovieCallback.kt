package com.khaledamin.moviesapplication.presentation.list

import com.khaledamin.moviesapplication.domain.model.Movie

interface MovieCallback {
    fun onMovieClicked(movie: Movie)
}