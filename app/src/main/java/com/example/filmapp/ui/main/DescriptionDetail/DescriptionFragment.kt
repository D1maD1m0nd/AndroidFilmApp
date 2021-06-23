package com.example.filmapp.ui.main.DescriptionDetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.filmapp.databinding.FragmentDescriptionBinding
import com.example.filmapp.model.entites.Film


class DescriptionFragment : Fragment() {
    private lateinit var binding: FragmentDescriptionBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDescriptionBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) = with(binding) {
        super.onViewCreated(view, savedInstanceState)
        val film = arguments?.getParcelable<Film>(BUNDLE_EXTRA)?.let {
            title.text = it.title
            genre.text = "Жанры: Боевик, комедия, мелодрама"
            duration.text = "Длительность: ${it.runtime}"
            vote.text = "Оценка: ${it.voteAverage}"
            budget.text = "Бюжет: ${it.budget}"
            revenue.text = "Сборы: ${it.revenue}"
            dateRealise.text = "Дата выпуска: ${it.dateReleased}"
            description.text = "Описание: ${it.overview}"
        }
    }

    companion object {
        const val BUNDLE_EXTRA = "FilmData"
        @JvmStatic
        fun newInstance(bundle: Bundle): DescriptionFragment {
            val fragment = DescriptionFragment()
            fragment.arguments = bundle
            return fragment
        }

    }
}