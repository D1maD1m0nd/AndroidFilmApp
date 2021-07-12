package com.example.filmapp.framework.main.ui.main_film_screen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.example.filmapp.R
import com.example.filmapp.databinding.MainFragmentBinding
import com.example.filmapp.framework.main.ui.descriptionDetail.DescriptionFragment
import com.example.filmapp.framework.main.ui.main_film_screen.adapter.MainAdapter
import com.example.filmapp.model.AppState
import com.example.filmapp.model.entites.Film
import org.koin.androidx.viewmodel.ext.android.viewModel

class FilmFragment : Fragment() {
    interface OnItemViewClickListener {
        fun onItemViewClick(film: Film)
    }

    companion object {
        @JvmStatic
        fun newInstance() = FilmFragment()
        private const val COUNT_COLUMN_RC = 2
    }


    private lateinit var binding: MainFragmentBinding
    private val viewModel: FilmViewModel by viewModel()

    private val onListItemClickListener = object : OnItemViewClickListener {
        override fun onItemViewClick(film: Film) {
            activity?.supportFragmentManager?.let {
                val bundle = Bundle()
                bundle.putParcelable(DescriptionFragment.BUNDLE_EXTRA, film)
                it.beginTransaction()
                    .add(R.id.container, DescriptionFragment.newInstance(bundle))
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

        rcView.layoutManager = GridLayoutManager(context, COUNT_COLUMN_RC)
        rcView.adapter = MainAdapter(onListItemClickListener).apply { addFilms(films) }
    }


}