package com.example.filmapp.ui.main.DescriptionDetail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.filmapp.R
import com.example.filmapp.databinding.FragmentDescriptionBinding
import com.example.filmapp.model.entites.Film


class DescriptionFragment : Fragment() {
    private lateinit var binding :FragmentDescriptionBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDescriptionBinding.inflate(inflater, container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) = with(binding){
        super.onViewCreated(view, savedInstanceState)
        val film = arguments?.getParcelable<Film>(BUNDLE_EXTRA)
        if(film != null) {
            title.text = film.title
            genre.text = "Жанры: Боевик, комедия, мелодрама"
            duration.text = "Длительность: ${film.runtime.toString()}"
            vote.text = "Оценка: ${film.voteAverage.toString()}"
            budget.text = "Бюжет: ${film.budget.toString()}"
            revenue.text = "Сборы: ${film.revenue.toString()}"
            dateRealise.text = "Дата выпуска: ${film.dateReleased}"
            description.text = "Описание: ${film.overview}"

        }
    }

    companion object {
        const val BUNDLE_EXTRA = "FilmData"
        fun newInstance(bundle: Bundle) :DescriptionFragment {
            val fragment = DescriptionFragment()
            fragment.arguments = bundle
            return fragment
        }

    }
}