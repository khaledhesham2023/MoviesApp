package com.khaledamin.moviesapplication.utils

sealed class State<out T>(val data: T? =null, val meesage:String? = null) {
    data object Loading : State<Nothing>()
    data class Success<out T>(val datas: T) : State<T>(data = datas)
    data class Error(val message:String): State<Nothing>(meesage = message)
}