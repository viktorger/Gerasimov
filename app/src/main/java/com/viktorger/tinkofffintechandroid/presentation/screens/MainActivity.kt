package com.viktorger.tinkofffintechandroid.presentation.screens

import android.os.Bundle
import android.view.Menu
import android.view.View
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.view.menu.MenuBuilder
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.viktorger.tinkofffintechandroid.NavGraphDirections
import com.viktorger.tinkofffintechandroid.R
import com.viktorger.tinkofffintechandroid.databinding.ActivityMainBinding
import com.viktorger.tinkofffintechandroid.presentation.screens.favorite.FavoriteViewModel
import com.viktorger.tinkofffintechandroid.presentation.screens.favorite.FavoriteViewModelFactory
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!

    private val vm: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        setupNavigation()
        initListeners()
    }



    private fun setupNavigation() {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        val navController = navHostFragment.navController

        val appBarConfiguration = AppBarConfiguration(setOf(R.id.popularFragment, R.id.favoriteFragment))
        binding.tbMain.setupWithNavController(navController, appBarConfiguration)

        binding.btnMain1.setOnClickListener {
            val action = NavGraphDirections.actionGlobalPopularFragment()
            navController.navigate(action)

            binding.btnMain1.configureSelected()
            binding.btnMain2.configureUnselected()
        }
        binding.btnMain2.setOnClickListener {
            val action = NavGraphDirections.actionGlobalFavoriteFragment()
            navController.navigate(action)

            binding.btnMain2.configureSelected()
            binding.btnMain1.configureUnselected()
        }
    }

    private fun Button.configureSelected() {
        setBackgroundColor(getColor(R.color.starfleet_blue))
        setTextColor(getColor(R.color.white))
    }

    private fun Button.configureUnselected() {
        setBackgroundColor(getColor(R.color.calcareous_sinter))
        setTextColor(getColor(R.color.starfleet_blue))
    }

    private fun initListeners() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                vm.showButtonsStateFlow.collectLatest {
                    binding.groupMainButtons.visibility = if (it) View.VISIBLE else View.GONE
                }
            }
        }

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.toolbar_menu, menu)

        return true
    }
}