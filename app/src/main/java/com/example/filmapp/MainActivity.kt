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

        if (savedInstanceState == null) {
            initBottomNavigationMenu()
        }
    }
    private fun isInternetAvailable(context: Context): Boolean {
        var result = false
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val networkCapabilities = connectivityManager.activeNetwork ?: return false
            val actNw =
                connectivityManager.getNetworkCapabilities(networkCapabilities) ?: return false
            result = when {
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
                else -> false
            }
        } else {
            connectivityManager.run {
                connectivityManager.activeNetworkInfo?.run {
                    result = when (type) {
                        ConnectivityManager.TYPE_WIFI -> true
                        ConnectivityManager.TYPE_MOBILE -> true
                        ConnectivityManager.TYPE_ETHERNET -> true
                        else -> false
                    }

                }
            }
        }

        return result
    }
    private fun initBottomNavigationMenu() = with(bind) {
        navView.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.home -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.container, HomeFragment.newInstance())
                        .commitAllowingStateLoss()
                    checkConnection()
                    true
                }
                R.id.main -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.container, FilmFragment.newInstance())
                        .commitAllowingStateLoss()
                    checkConnection()
                    true
                }
                R.id.likes -> {
                    //TODO удалить парметры в фабрике фрагмента
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.container, LikeFragment.newInstance("1", "2"))
                        .commitAllowingStateLoss()
                    checkConnection()
                    // Respond to navigation item 2 reselection
                    true
                }
                R.id.settings -> {
                    //TODO удалить парметры в фабрике фрагмента
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.container, SettingsFragment.newInstance("2", "3"))
                        .commitAllowingStateLoss()
                    checkConnection()
                    true
                }
                else -> false
            }
        }
        //для открытия страницы по дефолту
        navView.selectedItemId = R.id.main
    }
    private fun checkConnection() {
        val isConnect = isInternetAvailable(this)
        if(!isConnect){
            Toast.makeText(this, getString(R.string.no_connection_internet), Toast.LENGTH_SHORT).show()
        }
    }
}