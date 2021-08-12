package com.example.filmapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.filmapp.databinding.MainActivityBinding
import com.example.filmapp.framework.main.ui.home.HomeFragment
import com.example.filmapp.framework.main.ui.main_film_screen.FilmFragment
import com.example.filmapp.framework.main.ui.map_fragment.MapFragment
import com.example.filmapp.framework.main.ui.phones_fragment.PhonesListFragment
import com.example.filmapp.framework.main.ui.settings_fragment.SettingsFragment
import com.example.filmapp.utils.showFragment

class MainActivity : AppCompatActivity() {
    private lateinit var bind: MainActivityBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bind = MainActivityBinding.inflate(layoutInflater)
        setContentView(bind.root)
        initBottomNavigationMenu()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt(ITEM_SELECTED_TAG, bind.navView.selectedItemId)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        bind.navView.selectedItemId = savedInstanceState.getInt(ITEM_SELECTED_TAG, R.id.main)
    }

    private fun initBottomNavigationMenu() = with(bind) {
        navView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.home -> HomeFragment.newInstance().showFragment(this@MainActivity)
                R.id.main -> FilmFragment.newInstance().showFragment(this@MainActivity)
                R.id.maps -> MapFragment.newInstance().showFragment(this@MainActivity)
                R.id.settings -> SettingsFragment.newInstance().showFragment(this@MainActivity)
                R.id.phones -> PhonesListFragment.newInstance().showFragment(this@MainActivity)
                else -> FilmFragment.newInstance().showFragment(this@MainActivity)
            }
            return@setOnItemSelectedListener true
        }
        navView.selectedItemId = R.id.home
    }

    companion object {
        private const val ITEM_SELECTED_TAG = "SaveSelectItemNav"
    }
}