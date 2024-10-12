package com.khaledamin.moviesapplication.data.repository

import com.khaledamin.moviesapplication.data.local.MovieEntity
import com.khaledamin.moviesapplication.data.local.MoviesDao
import com.khaledamin.moviesapplication.data.remote.MoviesApi
import com.khaledamin.moviesapplication.data.remote.dto.MovieDTO
import com.khaledamin.moviesapplication.data.toMovie
import com.khaledamin.moviesapplication.data.toMovieEntity
import com.khaledamin.moviesapplication.domain.model.Movie
import com.khaledamin.moviesapplication.domain.repository.MoviesRepo
import javax.inject.Inject

class MoviesRepoImpl @Inject constructor(
    private val api: MoviesApi,
    private val dao: MoviesDao,
) : MoviesRepo {

//    override suspend fun getPopularMovies(
//        page: Int,
//        sortBy: String,
//        fetchFromRemote: Boolean,
//    ): Flow<State<ArrayList<Movie>>> {
//        var domainMovies = ArrayList<Movie>()
//        return flow {
//            val localMovies = dao.getMovies(sortBy)
//            if (sortBy == "favorites") {
//                val favorites = dao.getFavorites()
//                val favoritesMap = favorites.associateBy { it.id }
//                val favoriteMovies = favorites.map { favorite ->
//                    val existingFavorite = favoritesMap[favorite.id]
//                    favorite.toMovie(existingFavorite)
//                }.toCollection(ArrayList())
//                emit(State.Success(favoriteMovies))
//                return@flow
//            }
//            if (localMovies.isEmpty() || fetchFromRemote) {
//                val remoteMovies = try {
//                    api.getMovies(page = page, sortBy = sortBy).results
//                } catch (e: HttpException) {
//                    e.printStackTrace()
//                    emit(State.Error(message = e.message!!))
//                    return@flow
//                } catch (e: IOException) {
//                    e.printStackTrace()
//                    emit(State.Error(message = e.message!!))
//                    return@flow
//                }
//                val localMoviesMap = localMovies.associateBy { it.id }
//                val movies = remoteMovies!!.map { movie ->
//                    val existingEntity = localMoviesMap[movie.id]
//                    movie.toMovieEntity(existingEntity)
//                }
//                    .toCollection(ArrayList())
//                dao.insertAll(movies)
//                val databaseMoviesMap = movies.associateBy { it.id }
//                domainMovies = movies.map {
//                    val existingMovie = databaseMoviesMap[it.id]
//                    it.toMovie(existingMovie)
//                }.toCollection(ArrayList())
//                emit(State.Success(domainMovies))
//                return@flow
//            }
//            emit(State.Success(domainMovies))
//
//
////            val movies = dao.getPopularMovies()
////            val isDbEmpty = movies.isEmpty()
////            val shouldJustLoadFromCache = !isDbEmpty && !fetchFromRemote
////            if (shouldJustLoadFromCache) {
////                emit(State.Success(movies.map { it.toMovie() }.toCollection(ArrayList())))
////                return@flow
////            }
////            val networkMovies = try {
////                api.getMovies(page = page, sortBy = sortBy).results
////            } catch (e: HttpException) {
////                e.printStackTrace()
////                emit(State.Error(message = e.message!!))
////                return@flow
////            } catch (e: IOException) {
////                e.printStackTrace()
////                emit(State.Error(message = e.message!!))
////                return@flow
////            }
////            networkMovies?.let { movieDTOS ->
////                dao.clearAll()
////                val moviesToInsert =
////                    movieDTOS.map { movieDTO -> movieDTO.toMovieEntity() }.toCollection(ArrayList())
////                dao.insertAll(moviesToInsert)
////                Log.i("TAG", dao.getPopularMovies()[0].title!!)
////                emit(State.Success(moviesToInsert.map { it.toMovie() }.toCollection(ArrayList())))
////            }
//        }
//    }

    override suspend fun setMovieFavoriteOrNot(id: Long, isFavorite: Boolean) {
        dao.setFavoriteOrNot(id, isFavorite)
    }

    override suspend fun getMoviesFromRemoteSource(page: Int, sortBy: String): ArrayList<MovieDTO> {
        val movies = api.getMovies(page = page, sortBy = sortBy).results!!
        val localMoviesMap = dao.getMovies(sortBy).associateBy { it.id }
        dao.insertAll(movies.map {
            val existingMovie = localMoviesMap[it.id]
            it.toMovieEntity(existingMovie) }.toCollection(ArrayList()))
        return movies
    }

    override suspend fun getMoviesFromLocalCache(sortBy: String): ArrayList<MovieEntity> {
        val localMovies = dao.getMovies(sortBy).toCollection(ArrayList())
        return localMovies
    }

    override suspend fun getFavorites(): ArrayList<Movie> {
        val localMovies = dao.getFavorites()
        val localMoviesMap = dao.getFavorites().associateBy { it.id }
        return localMovies.map {
            val existingMovie = localMoviesMap[it.id]
            it.toMovie(existingMovie) }.toCollection(ArrayList())
    }

//    override fun getMoviesByPaging(sortBy: String, fetchFromRemote: Boolean): LiveData<PagingData<Movie>> {
//        return Pager(
//            config = PagingConfig(
//                pageSize = 10,
//                maxSize = 50
//            ),
//            pagingSourceFactory = { MoviesPagingSource(api, dao, sortBy, fetchFromRemote = fetchFromRemote) }
//        ).liveData
//    }
}