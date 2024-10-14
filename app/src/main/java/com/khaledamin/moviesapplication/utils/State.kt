package com.khaledamin.moviesapplication.utils

sealed class State<T>(val data: T? = null, val message: String? = null) {
    class Loading<T> : State<T>()
    class Success<T>(data: T) : State<T>(data)
    class Error<T>(message: String, data: T? = null) : State<T>(data, message)
}