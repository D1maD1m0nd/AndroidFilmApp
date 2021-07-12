package com.example.filmapp.framework.main.ui.settings

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.filmapp.R
import com.example.filmapp.databinding.FragmentSettingsBinding
import com.example.filmapp.databinding.MainFragmentBinding


class SettingsFragment : Fragment() {
    private lateinit var bind : FragmentSettingsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        bind = FragmentSettingsBinding.inflate(inflater, container, false)
        return bind.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bind.accept.setOnClickListener{
                saveFilters()
        }
        readPref()
    }

    private fun saveFilters(){
        val sharedPref = activity?.getPreferences(Context.MODE_PRIVATE)
       sharedPref?.edit()?.apply {
           bind.apply {
               putBoolean(FIGHTER_KEY, fighters.isChecked)
               putBoolean(TRILLER_KEY, triller.isChecked)
               putBoolean(COMEDY_KEY, comedy.isChecked )
               apply()
           }
        }
    }

    private fun readPref() {
        activity?.getPreferences(Context.MODE_PRIVATE)?.let {
            bind.apply {
                it.getBoolean(FIGHTER_KEY, false).also { fighters.isChecked = it }
                it.getBoolean(TRILLER_KEY, false).also { triller.isChecked = it }
                it.getBoolean(COMEDY_KEY, false).also { comedy.isChecked = it }
                it.getBoolean(DRAMA_KEY, false).also { drama.isChecked = it }
                it.getBoolean(SCREAMER_KEY, false).also { screamer.isChecked = it }

            }
        }
    }
    companion object {
        const val FIGHTER_KEY = "FIGHTER"
        const val TRILLER_KEY = "TRILLER"
        const val COMEDY_KEY = "COMEDY"
        const val SCREAMER_KEY = "SCREAMER"
        const val DRAMA_KEY = "DRAMA"
        val prefKeys = listOf<String>(FIGHTER_KEY,TRILLER_KEY,COMEDY_KEY,SCREAMER_KEY,DRAMA_KEY)
            @JvmStatic
        fun newInstance() = SettingsFragment()

    }
}