package com.khaledamin.moviesapplication.presentation.list

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.khaledamin.moviesapplication.R
import com.khaledamin.moviesapplication.databinding.FragmentMoviesListBinding
import com.khaledamin.moviesapplication.domain.model.Movie
import com.khaledamin.moviesapplication.presentation.abstracts.BaseFragment
import com.khaledamin.moviesapplication.presentation.callbacks.MovieCallback
import com.khaledamin.moviesapplication.presentation.callbacks.MovieFavoriteButtonCallback
import com.khaledamin.moviesapplication.presentation.callbacks.TabCallback
import com.khaledamin.moviesapplication.presentation.model.Tab
import com.khaledamin.moviesapplication.utils.NetworkUtil
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MoviesListFragment :
    BaseFragment<FragmentMoviesListBinding>(), TabCallback, MovieCallback,
    MovieFavoriteButtonCallback {
    override val layout: Int
        get() = R.layout.fragment_movies_list

    private val viewModel: MoviesListViewModel by viewModels()

    private lateinit var moviesAdapter: FavoritesAdapter
    private lateinit var tabsAdapter: TabsAdapter
    private lateinit var pagingAdapter: MoviesAdapter
    private var tabsList =
        arrayListOf(
            Tab(mapOf("Most Popular" to "popularity.desc"), false),
            Tab(mapOf("Highest Rated" to "vote_average.desc"), false),
            Tab(mapOf("Favorites" to "favorites"), false)
        )
    private lateinit var sortBy: String


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        moviesAdapter = FavoritesAdapter(requireContext(), ArrayList(), this, this)
        tabsAdapter = TabsAdapter(tabsList, this)
        sortBy = getSortBy(tabsList)
        pagingAdapter = MoviesAdapter(callback = {
            findNavController().navigate(
                MoviesListFragmentDirections.actionMoviesListFragmentToMovieDetailsFragment(
                    it
                )
            )
        }, this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupObservers()
        viewBinding.tabsList.adapter = tabsAdapter
        viewBinding.tabsList.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        viewBinding.moviesList.layoutManager = LinearLayoutManager(requireContext())
        if (sortBy == "favorites") {
            viewBinding.moviesList.adapter = moviesAdapter
            viewModel.getFavoriteMovies()
        } else {
            viewBinding.moviesList.adapter = pagingAdapter
            viewModel.getMoviesByPaging(sortBy, NetworkUtil.isInternetAvailable(requireContext()))
                .observe(viewLifecycleOwner) {
                    pagingAdapter.submitData(lifecycle, it)
                }
        }
    }

    private fun setupObservers() {
        viewModel.favoriteMoviesList.observe(viewLifecycleOwner) {
            moviesAdapter.updateData(it)
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
            if (it) {
                viewModel.getFavoriteMovies()
            }
        }
    }

    override fun onTabClicked(tab: Tab) {
        sortBy = tab.title.values.elementAt(0)
        if (sortBy == "favorites") {
            viewBinding.moviesList.adapter = moviesAdapter
            viewBinding.moviesList.layoutManager = LinearLayoutManager(requireContext())
            viewModel.getFavoriteMovies()
        } else {
            viewBinding.moviesList.adapter = pagingAdapter
            viewBinding.moviesList.layoutManager = LinearLayoutManager(requireContext())
            viewModel.getMoviesByPaging(
                sortBy,
                NetworkUtil.isInternetAvailable(requireContext())
            )
                .observe(viewLifecycleOwner) {

                    pagingAdapter.submitData(lifecycle, it)
                }
        }
    }

    override fun onMovieClicked(movie: Movie) {
        findNavController().navigate(
            MoviesListFragmentDirections.actionMoviesListFragmentToMovieDetailsFragment(
                movie
            )
        )
    }

    override fun onFavoriteButtonClicked(movie: Movie, isFavorite: Boolean) {
        viewModel.setMovieFavoriteOrNot(movie.id!!, isFavorite)
        viewModel.getFavoriteMovies()
    }

    private fun getSortBy(tabsList: ArrayList<Tab>): String {
        for (tab in tabsList) {
            if (tab.isSelected) {
                return tab.title.values.elementAt(0)
            }
        }
        return ""
    }
}