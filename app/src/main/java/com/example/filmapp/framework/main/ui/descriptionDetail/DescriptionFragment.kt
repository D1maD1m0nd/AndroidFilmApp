package com.example.filmapp.framework.main.ui.descriptionDetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.example.filmapp.R
import com.example.filmapp.databinding.FragmentDescriptionBinding
import com.example.filmapp.model.AppState
import com.example.filmapp.utils.RoundedTransformation
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
            arguments?.getInt(BUNDLE_EXTRA_INT)?.let {
                viewModel.getFilmForId(it)
            }
        }

    }

    private fun renderData(appState: AppState) {
        binding.apply {
            when (appState) {
                is AppState.SuccessId -> {
                    appState.filmData.let {
                        loadingLayout.visibility = View.GONE
                        var genresFormats = ""
                        it.genre?.forEach({ genresFormats += it.name + "," })
                        title.text = it.title
                        genre.text = "${getString(R.string.genre)} ${genresFormats.trimEnd(',')}"
                        duration.text =
                            "${getString(R.string.runtime)} ${it.runtime} ${getString(R.string.minutes)}"
                        vote.text = "${getString(R.string.vote_average)} ${it.voteAverage}"
                        budget.text = "${getString(R.string.budget)} ${it.budget}"
                        revenue.text = "${getString(R.string.revenue)} ${it.revenue}"
                        dateRealise.text = "${getString(R.string.date_realise)} ${it.dateReleased}"
                        description.text = "\t${it.overview}"
                        Picasso
                            .get()
                            .load("$imageStorageUrl${it.poster}")
                            .fit()
                            .transform(RoundedTransformation(RADIUS, DEFAULT_MARGIN))
                            .into(postersTitle)
                    }

                }
                is AppState.Loading -> {
                    loadingLayout.visibility = View.VISIBLE
                }
                is AppState.Error -> {
                    Toast.makeText(context, appState.error.message, Toast.LENGTH_SHORT).show()

                }
                is AppState.Success -> TODO()
            }
        }

    }

    companion object {
        const val BUNDLE_EXTRA_INT = "FilmId"
        private const val DEFAULT_MARGIN = 0
        private const val RADIUS = 25

        fun newInstance(bundle: Bundle): DescriptionFragment {
            val fragment = DescriptionFragment()
            fragment.arguments = bundle
            return fragment
        }

    }
}