package com.example.filmapp.framework.main.ui.home

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.filmapp.R
import com.example.filmapp.databinding.FragmentHomeBinding
import com.example.filmapp.framework.main.ui.descriptionDetail.DescriptionFragment
import com.example.filmapp.framework.main.ui.home.Adapters.Item
import com.example.filmapp.framework.main.ui.home.Adapters.MainHomeAdapter
import com.example.filmapp.framework.main.ui.main_film_screen.FilmFragment
import com.example.filmapp.framework.main.ui.settings.SettingsFragment
import com.example.filmapp.framework.main.ui.settings.SettingsFragment.Companion.prefKeys
import com.example.filmapp.model.AppState
import com.example.filmapp.model.entites.Film
import org.koin.androidx.viewmodel.ext.android.viewModel


class HomeFragment : Fragment() {

    private val onListItemClickListener = object : FilmFragment.OnItemViewClickListener {
        override fun onItemViewClick(film: Film) {
            activity?.supportFragmentManager?.let {
                val bundle = Bundle()
                bundle.putInt(DescriptionFragment.BUNDLE_EXTRA_INT, film.id ?: DEFAULT_ID)
                it.beginTransaction()
                    .add(R.id.container, DescriptionFragment.newInstance(bundle))
                    .addToBackStack(null)
                    .commitAllowingStateLoss()
            }
        }
    }
    private lateinit var binding: FragmentHomeBinding
    private val viewModel: HomeFragmentViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val observer = Observer<AppState> {
            renderData(it)
        }
        viewModel.filtersGenre = getFilters()
        viewModel.liveDataToObserve.observe(viewLifecycleOwner, observer)
        viewModel.getPopularFilms()
    }

    private fun renderData(appState: AppState) {
        when (appState) {
            is AppState.Success -> {
                val filmsData = appState.filmsData
                initRcView(filmsData)
            }
            is AppState.Loading -> {
            }
            is AppState.Error -> {
                Toast.makeText(context, appState.error.message, Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun initRcView(films: ArrayList<Film>) = with(binding) {
        val items = ArrayList<Item>().apply { add(Item(films, getString(R.string.pupularity))) }
        rcView.hasFixedSize()
        rcView.layoutManager = LinearLayoutManager(context)
        rcView.adapter = MainHomeAdapter(onListItemClickListener).apply { addItems(items) }
    }
    private fun getFilters() : ArrayList<Int>{
        val ids = ArrayList<Int>()
        activity?.getPreferences(Context.MODE_PRIVATE)?.let{
            for (key in prefKeys) {
                val isEnable = it.getBoolean(key, false)
                if(isEnable) {
                    genres[key]?.let { it1 -> ids.add(it1) }
                }
            }
        }
        return ids
    }
    companion object {
        private  val genres = mapOf(
            SettingsFragment.DRAMA_KEY to 18,
            SettingsFragment.COMEDY_KEY to 35,
            SettingsFragment.TRILLER_KEY to 53,
            SettingsFragment.SCREAMER_KEY to 27,
            SettingsFragment.FIGHTER_KEY to 28
        )
        const val DEFAULT_ID = 550
        @JvmStatic
        fun newInstance() = HomeFragment()

    }
}