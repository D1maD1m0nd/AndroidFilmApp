package com.example.filmapp.framework.main.ui.descriptionDetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.example.filmapp.databinding.FragmentDescriptionBinding
import com.example.filmapp.model.AppState
import com.example.filmapp.model.entites.Film
import com.squareup.picasso.Picasso
import org.koin.androidx.viewmodel.ext.android.viewModel


class DescriptionFragment : Fragment() {
    private lateinit var binding: FragmentDescriptionBinding
    private val imageStorageUrl = "https://image.tmdb.org/t/p/w500/"
    private val viewModel: DescriptionViewModel by viewModel()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDescriptionBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val observer = Observer<AppState> {
            renderData(it)
        }
        viewModel.liveDataToObserve.observe(viewLifecycleOwner, observer)

        //сделано для того что бы не писать отдельную переменную под возвращающее значение binding
        binding.apply {
            arguments?.getParcelable<Film>(BUNDLE_EXTRA)?.let {
                var genresFormats = ""
                it.genre?.forEach({ genresFormats += it.name + "," })
                title.text = it.title
                genre.text = "Жанры: ${genresFormats.trimEnd(',')}"
                duration.text = "Длительность: ${it.runtime} m"
                vote.text = "Оценка: ${it.voteAverage}"
                budget.text = "Бюжет: ${it.budget}$"
                revenue.text = "Сборы: ${it.revenue}$"
                dateRealise.text = "Дата выпуска: ${it.dateReleased}"
                description.text = "Описание: ${it.overview}"
                Picasso
                    .get()
                    .load("$imageStorageUrl${it.poster}")
                    .fit()
                    .into(postersTitle)
            }

            arguments?.getInt(BUNDLE_EXTRA_INT)?.let {
                viewModel.getFilmForId(it)
            }
        }

    }

    private fun renderData(appState: AppState) {
        when (appState) {
            is AppState.SuccessId -> {
                //loadingLayout.visibility = View.GONE
                appState.filmData.let {
                    binding.apply {
                        var genresFormats = ""
                        it.genre?.forEach({ genresFormats += it.name + "," })
                        title.text = it.title
                        genre.text = "Жанры: ${genresFormats.trimEnd(',')}"
                        duration.text = "Длительность: ${it.runtime} m"
                        vote.text = "Оценка: ${it.voteAverage}"
                        budget.text = "Бюжет: ${it.budget}$"
                        revenue.text = "Сборы: ${it.revenue}$"
                        dateRealise.text = "Дата выпуска: ${it.dateReleased}"
                        description.text = "Описание: ${it.overview}"
                        Picasso
                            .get()
                            .load("$imageStorageUrl${it.poster}")
                            .fit()
                            .into(postersTitle)
                    }
                }
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

    companion object {
        const val BUNDLE_EXTRA = "FilmData"
        const val BUNDLE_EXTRA_INT = "FilmId"

        @JvmStatic
        fun newInstance(bundle: Bundle): DescriptionFragment {
            val fragment = DescriptionFragment()
            fragment.arguments = bundle
            return fragment
        }

    }
}