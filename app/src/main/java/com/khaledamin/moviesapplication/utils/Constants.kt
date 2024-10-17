package com.khaledamin.moviesapplication.utils

import com.khaledamin.moviesapplication.presentation.state.TabState

object Constants {
    const val AUTHORIZATION_KEY = "Authorization"
    const val DATABASE_NAME = "movies_db"
    const val TABLE_NAME = "movies"
    val MOVIES_TABS = TabState.entries.toTypedArray()
}