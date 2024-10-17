package com.khaledamin.moviesapplication.presentation

import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import com.google.android.material.tabs.TabLayout
import com.khaledamin.moviesapplication.R
import com.khaledamin.moviesapplication.databinding.ActivityMainBinding
import com.khaledamin.moviesapplication.presentation.abstracts.BaseActivity
import com.khaledamin.moviesapplication.presentation.state.TabState
import com.khaledamin.moviesapplication.presentation.tabs.TabSelectionListener
import com.khaledamin.moviesapplication.utils.Constants
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>() {

    override val layout: Int
        get() = R.layout.activity_main

    private lateinit var navController: NavController
    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupTabs()
        setupNavigation()
        window.navigationBarColor = ContextCompat.getColor(this, R.color.midnight_blue)

    }

    private fun setupNavigation() {
        navController =
            (supportFragmentManager.findFragmentById(R.id.nav_host_fragment_container) as NavHostFragment).findNavController()
        appBarConfiguration = AppBarConfiguration.Builder(R.id.moviesListFragment).build()
        setSupportActionBar(viewBinding.toolbar)
        setupActionBarWithNavController(navController, appBarConfiguration)
        navController.addOnDestinationChangedListener { _, destination, _ ->
            if (destination.id == R.id.movieDetailsFragment) {
                viewBinding.tabsList.visibility = View.GONE
            } else {
                viewBinding.tabsList.visibility = View.VISIBLE
            }
        }
    }

    private fun setupTabs() {
        Constants.MOVIES_TABS.forEach { tabData ->
            val tab = viewBinding.tabsList.newTab().setText(tabData.title)
            viewBinding.tabsList.addTab(tab)
        }
        viewBinding.tabsList.addOnTabSelectedListener(object : TabSelectionListener(){
            override fun onTabSelected(tab: TabLayout.Tab?) {
                if (tab != null) {
                    navController.navigate(MainActivityDirections.actionMainActivityToMoviesListFragment(TabState.entries[tab.position].sortBy))
                }
            }
        })
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }
}