package com.example.filmapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.filmapp.ui.main.Likes.LikeFragment
import com.example.filmapp.ui.main.Main.MainFragment
import com.example.filmapp.ui.main.Settings.SettingsFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        if (savedInstanceState == null) {
            val containerNav : BottomNavigationView = findViewById(R.id.nav_view)
            containerNav.setOnNavigationItemSelectedListener {item ->
                when(item.itemId) {
                    R.id.page_1 -> {
                        supportFragmentManager.beginTransaction()
                            .replace(R.id.container, MainFragment.newInstance())
                            .commitNow()
                        true
                    }

                    R.id.page_2 -> {
                        //TODO удалить парметры в фабрике фрагмента
                        supportFragmentManager.beginTransaction()
                            .replace(R.id.container, LikeFragment.newInstance("1", "2"))
                            .commitNow()
                        // Respond to navigation item 2 reselection
                        true
                    }
                    R.id.page_3 -> {
                        //TODO удалить парметры в фабрике фрагмента
                        supportFragmentManager.beginTransaction()
                            .replace(R.id.container, SettingsFragment.newInstance("2", "3"))
                            .commitNow()
                        true
                    }
                    else -> false
                }

            }
        }
    }
}