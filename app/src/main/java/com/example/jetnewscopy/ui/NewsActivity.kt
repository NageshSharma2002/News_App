package com.example.jetnewscopy.ui

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.jetnewscopy.R
import com.example.jetnewscopy.databinding.ActivityNewsBinding
import com.example.jetnewscopy.db.ArticleDatabase
import com.example.jetnewscopy.repository.NewsRepository
import com.google.android.material.navigation.NavigationView

class NewsActivity : AppCompatActivity() {

    lateinit var newsViewModel: NewsViewModel
    lateinit var binding: ActivityNewsBinding
    lateinit var drawerLayout: DrawerLayout
    lateinit var navView: NavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNewsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Set the Toolbar as the ActionBar
        setSupportActionBar(binding.toolbar)

        // Explicitly clear any default title
       supportActionBar?.title = null
       supportActionBar?.setDisplayShowTitleEnabled(false)

        // Show only the back arrow
       supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val newsRepository = NewsRepository(ArticleDatabase(this))
        val viewModelProviderFactory = NewsViewModelProviderFactory(application, newsRepository)
        newsViewModel =
            ViewModelProvider(this, viewModelProviderFactory).get(NewsViewModel::class.java)

        drawerLayout = binding.drawerLayout
        navView = binding.navView

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.newsNavHostFragment) as NavHostFragment
        val navController = navHostFragment.navController

        // Setup ActionBar with NavigationController and DrawerLayout
        setupActionBarWithNavController(navController, drawerLayout)

        // Setup NavigationView with NavController
        navView.setupWithNavController(navController)
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.newsNavHostFragment)
        return navController.navigateUp(drawerLayout) || super.onSupportNavigateUp()
    }
}
