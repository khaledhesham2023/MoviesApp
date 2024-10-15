package com.khaledamin.moviesapplication.domain.usecases

import com.khaledamin.moviesapplication.domain.repository.MoviesRepo
import javax.inject.Inject

class NetworkConnectionUseCase @Inject constructor(
    private val repo: MoviesRepo
) {
    fun checkNetworkConnection():Boolean = repo.checkConnection()
}