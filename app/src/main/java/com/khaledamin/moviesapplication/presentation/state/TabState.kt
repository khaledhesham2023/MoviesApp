package com.khaledamin.moviesapplication.presentation.state

enum class TabState(var title: String, var sortBy:String) {
    MOST_POPULAR("Most Popular", "popularity.desc"),
    TOP_RATED("Top Rated", "vote_average.desc"),
    FAVORITES("Favorites","favorites")
}