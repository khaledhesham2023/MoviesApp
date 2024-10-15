package com.khaledamin.moviesapplication.presentation.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.liveData
import com.khaledamin.moviesapplication.domain.model.Movie
import com.khaledamin.moviesapplication.domain.usecases.GetFavoriteMoviesUseCase
import com.khaledamin.moviesapplication.domain.usecases.GetMoviesByPagingUseCases
import com.khaledamin.moviesapplication.domain.usecases.SetMovieFavoriteUseCase
import com.khaledamin.moviesapplication.data.remote.NetworkUtil
import com.khaledamin.moviesapplication.domain.usecases.NetworkConnectionUseCase
import com.khaledamin.moviesapplication.utils.State
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MoviesListViewModel @Inject constructor(
    private val favoriteUseCase: SetMovieFavoriteUseCase,
    private val pagingUseCases: GetMoviesByPagingUseCases,
    private val favoriteMoviesUseCase: GetFavoriteMoviesUseCase,
    private val networkUseCase: NetworkConnectionUseCase,
) : ViewModel() {

    private var _favoriteMoviesList = MutableLiveData<State<ArrayList<Movie>>>()
    val favoriteMoviesList: LiveData<State<ArrayList<Movie>>>
        get() = _favoriteMoviesList

    private val _showProgress = MutableLiveData<Boolean>()
    val showProgress: LiveData<Boolean>
        get() = _showProgress

    private val _showToast = MutableLiveData<String>()
    val showToast: LiveData<String>
        get() = _showToast

    private val _setFavoriteLiveData = MutableLiveData<Boolean>()
    val setFavoriteLiveData: LiveData<Boolean>
        get() = _setFavoriteLiveData

    fun getMoviesByPaging(sortBy: String, fetchFromRemote: Boolean): LiveData<PagingData<Movie>> {
        var pager: LiveData<PagingData<Movie>>? = null
        _showProgress.value = true
        viewModelScope.launch {
            _showProgress.value = true
            pager = pagingUseCases.invoke(sortBy, fetchFromRemote).liveData
            _showProgress.value = false

        }
        return pager!!
    }

    fun getFavoriteMovies() {
        _favoriteMoviesList.value = State.Loading()
        viewModelScope.launch {
            try {
                val response = favoriteMoviesUseCase.invoke()
                _favoriteMoviesList.value = State.Success(data = response)
                _showProgress.value = false
            } catch (e: Exception) {
                _favoriteMoviesList.value = State.Error(message = e.message!!)
            }
        }
    }

    fun setMovieFavoriteOrNot(id: Long, isFavorite: Boolean) = viewModelScope.launch {
        try {
            _showProgress.value = true
            favoriteUseCase.invoke(id, isFavorite)
            _setFavoriteLiveData.postValue(true)
            _showProgress.value = false
        } catch (e: Exception) {
            _setFavoriteLiveData.postValue(false)
            _showProgress.value = false
        }
        _showProgress.value = false
    }

    fun checkNetworkConnection(): Boolean {
        return networkUseCase.checkNetworkConnection()
    }
}