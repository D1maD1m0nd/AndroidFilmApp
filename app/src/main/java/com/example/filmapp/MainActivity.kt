package com.example.filmapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.filmapp.databinding.MainActivityBinding
import com.example.filmapp.ui.main.Likes.LikeFragment
import com.example.filmapp.ui.main.main_film_screen.MainFragment
import com.example.filmapp.ui.main.Settings.SettingsFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.main_activity.*

class MainActivity : AppCompatActivity() {
    lateinit var bind : MainActivityBinding
    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        bind = MainActivityBinding.inflate(layoutInflater)
        setContentView(bind.root)
        if (savedInstanceState == null) {
            initBottomNavigationMenu()
        }
    }
    private fun initBottomNavigationMenu() = with(bind){


        navView.setOnNavigationItemSelectedListener {item ->
            when(item.itemId) {
                R.id.page_1 -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.container, MainFragment.newInstance())
                        .commitAllowingStateLoss()
                    true
                }
                R.id.page_2 -> {
                    //TODO удалить парметры в фабрике фрагмента
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.container, LikeFragment.newInstance("1", "2"))
                        .commitAllowingStateLoss()
                    // Respond to navigation item 2 reselection
                    true
                }
                R.id.page_3 -> {
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
        navView.selectedItemId = R.id.page_1;
    }
}