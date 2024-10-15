package com.khaledamin.moviesapplication.presentation.details

import android.os.Bundle
import android.view.View
import com.khaledamin.moviesapplication.R
import com.khaledamin.moviesapplication.databinding.FragmentMovieDetailsBinding
import com.khaledamin.moviesapplication.domain.model.Movie
import com.khaledamin.moviesapplication.presentation.abstracts.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MovieDetailsFragment : BaseFragment<FragmentMovieDetailsBinding>() {
    override val layout: Int
        get() = R.layout.fragment_movie_details
    private lateinit var movie: Movie

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        movie = MovieDetailsFragmentArgs.fromBundle(requireArguments()).movie
        viewBinding.movie = movie
    }
}