package com.example.filmapp.ui.main.main_film_screen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.filmapp.R
import com.example.filmapp.databinding.MainFragmentBinding
import com.example.filmapp.model.AppState
import com.example.filmapp.model.entites.Film
import com.example.filmapp.ui.main.descriptionDetail.DescriptionFragment
import com.example.filmapp.ui.main.main_film_screen.adapter.MainAdapter

class FilmFragment : Fragment() {
    interface OnItemViewClickListener {
        fun onItemViewClick(film: Film)
    }

    companion object {
        @JvmStatic
        fun newInstance() = FilmFragment()
    }


    private lateinit var binding: MainFragmentBinding
    private val viewModel: FilmViewModel by lazy {
        ViewModelProvider(this).get(FilmViewModel::class.java)
    }
    private val onListItemClickListener = object : OnItemViewClickListener {
        override fun onItemViewClick(film: Film) {
            activity?.supportFragmentManager?.let {
                val bundle = Bundle()
                bundle.putParcelable(DescriptionFragment.BUNDLE_EXTRA, film)
                it.beginTransaction()
                    .replace(R.id.container, DescriptionFragment.newInstance(bundle))
                    .addToBackStack(null)
                    .commitAllowingStateLoss()
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = MainFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val observer = Observer<AppState> {
            renderData(it)
        }
        viewModel.getLiveData().observe(viewLifecycleOwner, observer)
        viewModel.getFilmLocalSource()
    }

    private fun renderData(appState: AppState) = with(binding) {
        when (appState) {
            is AppState.Success -> {
                loadingLayout.visibility = View.GONE
                val filmsData = appState.filmsData
                initRcView(filmsData)
            }
            is AppState.Loading -> {
                loadingLayout.visibility = View.VISIBLE
            }
            is AppState.Error -> {
                loadingLayout.visibility = View.GONE
            }
        }
    }

    private fun initRcView(films: ArrayList<Film>) = with(binding) {

        rcView.layoutManager = GridLayoutManager(context, 3)
        val adapter = MainAdapter(onListItemClickListener)
        adapter.addFilms(films)
        rcView.adapter = adapter
    }

}