package com.example.filmapp.framework.main.ui.home

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
import com.example.filmapp.model.AppState
import com.example.filmapp.model.entites.Film
import org.koin.androidx.viewmodel.ext.android.viewModel


class HomeFragment : Fragment() {

    private val onListItemClickListener = object : FilmFragment.OnItemViewClickListener {
        override fun onItemViewClick(film: Film) {
            activity?.supportFragmentManager?.let {
                val bundle = Bundle()
                bundle.putInt(DescriptionFragment.BUNDLE_EXTRA_INT, film.id ?: 550)
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

        viewModel.liveDataToObserve.observe(viewLifecycleOwner, observer)
        viewModel.getPopularFilms()
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
        rcView.hasFixedSize()
        rcView.layoutManager = LinearLayoutManager(context)
        rcView.adapter = MainHomeAdapter(onListItemClickListener).apply { addItems(items) }
    }

    companion object {
        @JvmStatic
        fun newInstance() = HomeFragment()

    }
}