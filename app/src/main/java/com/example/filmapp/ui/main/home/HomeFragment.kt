package com.example.filmapp.ui.main.home

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.filmapp.databinding.FragmentHomeBinding
import com.example.filmapp.model.AppState
import com.example.filmapp.model.entites.Film
import com.example.filmapp.services.ConstService.DETAILS_INTENT_FILTER
import com.example.filmapp.services.ConstService.DETAILS_LOAD_RESULT_EXTRA
import com.example.filmapp.services.ConstService.DETAILS_RESPONSE_SUCCESS_EXTRA
import com.example.filmapp.services.ConstService.DETAILS_TEMP_EXTRA
import com.example.filmapp.services.PopularFilmService
import com.example.filmapp.ui.main.home.adapters.Item
import com.example.filmapp.ui.main.home.adapters.MainHomeAdapter


class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding

    private val loadResultsReceiver: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            when (intent.getStringExtra(DETAILS_LOAD_RESULT_EXTRA)) {
                DETAILS_RESPONSE_SUCCESS_EXTRA -> intent.getParcelableArrayListExtra<Film>(DETAILS_TEMP_EXTRA)?.let {
                    renderData(
                        it
                    )
                }
                else -> false
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        context?.let {
            LocalBroadcastManager.getInstance(it)
                .registerReceiver(loadResultsReceiver, IntentFilter(DETAILS_INTENT_FILTER))
        }
    }
    override fun onDestroy() {
        context?.let {
            LocalBroadcastManager.getInstance(it).unregisterReceiver(loadResultsReceiver)
        }
        super.onDestroy()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getFilms()
    }

    private fun getFilms() {
        context?.let {
            it.startService(Intent(it, PopularFilmService::class.java))
        }
    }
    private fun renderData(data:ArrayList<Film>) {
            initRcView(data)
    }
    private fun renderData(appState: AppState) {
        when (appState) {
            is AppState.Success -> {
                //loadingLayout.visibility = View.GONE
                val filmsData = appState.filmsData
                initRcView(filmsData)
            }
            is AppState.Loading -> {
                //loadingLayout.visibility = View.VISIBLE
            }
            is AppState.Error -> {
                Toast.makeText(context, appState.error.message, Toast.LENGTH_SHORT).show()


                //loadingLayout.visibility = View.GONE
            }
        }
    }

    private fun initRcView(films: ArrayList<Film>) = with(binding) {
        val items = ArrayList<Item>().apply { add(Item(films, "Популярные")) }
        rcView.layoutManager = LinearLayoutManager(context)
        rcView.adapter = MainHomeAdapter().apply { addItems(items) }
    }

    companion object {
        @JvmStatic
        fun newInstance() = HomeFragment()
    }
}