package com.khaledamin.moviesapplication.presentation.list

//import com.khaledamin.moviesapplication.domain.usecases.GetMoviesUseCases
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
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MoviesListViewModel @Inject constructor(
//    private val useCases: GetMoviesUseCases,
    private val favoriteUseCase: SetMovieFavoriteUseCase,
    private val pagingUseCases: GetMoviesByPagingUseCases,
    private val favoriteMoviesUseCase: GetFavoriteMoviesUseCase,
) : ViewModel() {

    private var _favoriteMoviesList = MutableLiveData<ArrayList<Movie>>()
    val favoriteMoviesList: LiveData<ArrayList<Movie>>
        get() = _favoriteMoviesList

    private val _showProgress = MutableLiveData<Boolean>()
    val showProgress: LiveData<Boolean>
        get() = _showProgress

    private val _showToast = MutableLiveData<String>()
    val showToast: LiveData<String>
        get() = _showToast

    fun getMoviesByPaging(sortBy: String, fetchFromRemote: Boolean): LiveData<PagingData<Movie>> {
        _showProgress.value = true
        val pager = pagingUseCases.invoke(sortBy, fetchFromRemote).liveData
        _showProgress.value = false
        return pager
    }

//    private var _moviesList = MutableLiveData<PagingData<Movie>>()
//    val moviesList: LiveData<PagingData<Movie>>
//        get() = _moviesList

//    fun getPopularMovies(page: Int, sortBy: String, fetchFromRemote: Boolean) =
//        viewModelScope.launch {
//            _showProgress.value = true
//            useCases.invoke(page, sortBy, fetchFromRemote).collectLatest {
//                when (it) {
//                    is State.Success -> {
//                        _moviesList.value = it.data!!
//                        _showProgress.value = false
//                    }
//
//                    is State.Error -> {
//                        _showProgress.value = false
//                        _showToast.value = it.message
//                    }
//                }
//            }
//            _showProgress.value = false
//        }

    fun getFavoriteMovies() = viewModelScope.launch {
        _favoriteMoviesList.value = favoriteMoviesUseCase.invoke()
    }

//    fun getPopularMovies(page: Int, sortBy: String): Flow<PagingData<Movie>> =
//        viewModelScope.launch {
//
//        }
//    _showProgress.value = true
//    _showToast.value = false
//    val pager = useCases.invoke(page, sortBy).flow.cachedIn(viewModelScope)
//    _showProgress.value = false
//    return pager
//}

    fun setMovieFavoriteOrNot(id: Long, isFavorite: Boolean) = viewModelScope.launch {
        _showProgress.value = true
        favoriteUseCase.invoke(id, isFavorite)
        _showProgress.value = false
    }
}