package com.example.filmapp

import android.content.Context
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.filmapp.databinding.MainActivityBinding
import com.example.filmapp.services.ConnectionChangeService

import com.example.filmapp.ui.main.home.HomeFragment
import com.example.filmapp.ui.main.likes.LikeFragment
import com.example.filmapp.ui.main.main_film_screen.FilmFragment
import com.example.filmapp.ui.main.settings.SettingsFragment

class MainActivity : AppCompatActivity() {
    private lateinit var bind: MainActivityBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bind = MainActivityBinding.inflate(layoutInflater)
        setContentView(bind.root)
        registerReceiver(ConnectionChangeService(), IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION))

        if (savedInstanceState == null) {
            initBottomNavigationMenu()
        }
    }

    private fun initBottomNavigationMenu() = with(bind) {
        navView.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.home -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.container, HomeFragment.newInstance())
                        .commitAllowingStateLoss()
                    true
                }
                R.id.main -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.container, FilmFragment.newInstance())
                        .commitAllowingStateLoss()
                    true
                }
                R.id.likes -> {
                    //TODO удалить парметры в фабрике фрагмента
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.container, LikeFragment.newInstance("1", "2"))
                        .commitAllowingStateLoss()
                    // Respond to navigation item 2 reselection
                    true
                }
                R.id.settings -> {
                    //TODO удалить парметры в фабрике фрагмента
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.container, SettingsFragment.newInstance("2", "3"))
                        .commitAllowingStateLoss()
                    true
                }
                else -> false
            }
        }
        //для открытия страницы по дефолту
        navView.selectedItemId = R.id.main
    }

}