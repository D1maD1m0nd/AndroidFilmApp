package com.example.filmapp

import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.filmapp.databinding.MainActivityBinding
import com.example.filmapp.framework.main.ui.home.HomeFragment
import com.example.filmapp.framework.main.ui.main_film_screen.FilmFragment
import com.example.filmapp.framework.main.ui.map_fragment.MapFragment
import com.example.filmapp.framework.main.ui.phones_fragment.PhonesListFragment
import com.example.filmapp.framework.main.ui.settings_fragment.SettingsFragment
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging

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
        outState.putInt(ITEM_SELECTED_TAG,bind.navView.selectedItemId)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        bind.navView.selectedItemId = savedInstanceState.getInt(ITEM_SELECTED_TAG, R.id.main)
    }
    private fun initBottomNavigationMenu() = with(bind) {
        navView.setOnItemSelectedListener  { item ->
            when (item.itemId) {
                R.id.home -> {
                    openFragment(HomeFragment.newInstance())
                    true
                }
                R.id.main -> {
                    openFragment(FilmFragment.newInstance())
                    true
                }
                R.id.maps -> {
                    openFragment(MapFragment.newInstance())
                    supportFragmentManager.beginTransaction()
                    true
                }
                R.id.settings -> {
                    openFragment(SettingsFragment.newInstance())
                    true
                }
                R.id.phones -> {
                    openFragment(PhonesListFragment.newInstance())
                    true
                }
                else -> false
            }
        }

        //для открытия страницы по дефолту
        navView.selectedItemId = R.id.main
    }

    private fun openFragment(fragment: Fragment) {
        //TODO удалить парметры в фабрике фрагмента
        supportFragmentManager.beginTransaction()
            .replace(R.id.container, fragment)
            .commitAllowingStateLoss()
    }

    companion object {
        private const val ITEM_SELECTED_TAG = "SaveSelectItemNav"
    }
}