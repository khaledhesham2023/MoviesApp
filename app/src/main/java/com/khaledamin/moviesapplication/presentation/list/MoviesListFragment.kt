package com.khaledamin.moviesapplication.presentation.list

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.khaledamin.moviesapplication.R
import com.khaledamin.moviesapplication.databinding.FragmentMoviesListBinding
import com.khaledamin.moviesapplication.presentation.abstracts.BaseFragment
import com.khaledamin.moviesapplication.presentation.state.TabState
import com.khaledamin.moviesapplication.utils.State
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MoviesListFragment : BaseFragment<FragmentMoviesListBinding>() {
    override val layout: Int
        get() = R.layout.fragment_movies_list

    private val viewModel: MoviesListViewModel by viewModels()
    private lateinit var moviesAdapter: FavoriteMoviesAdapter
    private lateinit var pagingAdapter: PagedMoviesAdapter
    private lateinit var sortBy: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupAdapters()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupObservers()
        sortBy = MoviesListFragmentArgs.fromBundle(requireArguments()).sortBy
        configureMovieList(sortBy)
    }

    private fun setupObservers() {
        viewModel.favoriteMoviesList.observe(viewLifecycleOwner) {
            when (it) {
                is State.Loading -> {
                    viewBinding.progressBar.visibility = View.VISIBLE
                }

                is State.Success -> {
                    moviesAdapter.updateData(it.data!!)
                }

                is State.Error -> {
                    Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
        viewModel.showToast.observe(viewLifecycleOwner) {
            Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
        }
        viewModel.showProgress.observe(viewLifecycleOwner) {
            if (it) {
                viewBinding.progressBar.visibility = View.VISIBLE
            } else {
                viewBinding.progressBar.visibility = View.GONE
            }
        }
        viewModel.setFavoriteLiveData.observe(viewLifecycleOwner) {
            when (it) {
                is State.Loading -> {
                    viewBinding.progressBar.visibility = View.VISIBLE
                }

                is State.Success -> {
                    viewBinding.progressBar.visibility = View.GONE
                    viewModel.getFavoriteMovies()
                }

                is State.Error -> {
                    viewBinding.progressBar.visibility = View.GONE
                }
            }
        }
    }
    private fun setupAdapters() {
        moviesAdapter = FavoriteMoviesAdapter(oldList = ArrayList(), navigator = { movie ->
            findNavController().navigate(
                MoviesListFragmentDirections.actionMoviesListFragmentToMovieDetailsFragment(
                    movie
                )
            )
        }, removeFromFavorite = { movie, isFavorite ->
            movie.id?.let {
                viewModel.setFavoriteState(it, isFavorite)
            }
        })
        pagingAdapter = PagedMoviesAdapter(navigator = { movie ->
            findNavController().navigate(
                MoviesListFragmentDirections.actionMoviesListFragmentToMovieDetailsFragment(
                    movie = movie
                )
            )
        }, setFavorite = { movie, isFavorite ->
            movie.id?.let { viewModel.setFavoriteState(it, isFavorite) }
        })
    }

    private fun configureMovieList(sortBy: String) {
        when {
            sortBy == TabState.FAVORITES.sortBy -> {
                viewBinding.moviesList.adapter = moviesAdapter
                viewModel.getFavoriteMovies()
            }
            else -> {
                viewBinding.moviesList.adapter = pagingAdapter
                viewModel.getMovies(sortBy, viewModel.checkNetworkConnection())
                    .observe(viewLifecycleOwner) {
                        pagingAdapter.submitData(lifecycle, it)
                    }
            }
        }
    }
}