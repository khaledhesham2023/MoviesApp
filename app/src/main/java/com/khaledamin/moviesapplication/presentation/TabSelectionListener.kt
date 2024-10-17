package com.khaledamin.moviesapplication.presentation

import com.google.android.material.tabs.TabLayout

abstract class TabSelectionListener : TabLayout.OnTabSelectedListener {
    override fun onTabSelected(tab: TabLayout.Tab?) {}

    override fun onTabUnselected(tab: TabLayout.Tab?) {}

    override fun onTabReselected(tab: TabLayout.Tab?) {}
}