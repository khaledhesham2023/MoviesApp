package com.khaledamin.moviesapplication.presentation.list

import com.khaledamin.moviesapplication.presentation.model.Tab

interface TabCallback {

    fun onTabClicked(tab: Tab)
}