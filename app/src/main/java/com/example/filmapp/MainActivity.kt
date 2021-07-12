package com.example.filmapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.filmapp.databinding.MainActivityBinding
import com.example.filmapp.framework.main.ui.home.HomeFragment
import com.example.filmapp.framework.main.ui.likes.LikeFragment
import com.example.filmapp.framework.main.ui.main_film_screen.FilmFragment
import com.example.filmapp.framework.main.ui.settings.SettingsFragment

class MainActivity : AppCompatActivity() {
    private lateinit var bind: MainActivityBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bind = MainActivityBinding.inflate(layoutInflater)
        setContentView(bind.root)
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
                    true
                }
                R.id.settings -> {
                    //TODO удалить парметры в фабрике фрагмента
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.container, SettingsFragment.newInstance())
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