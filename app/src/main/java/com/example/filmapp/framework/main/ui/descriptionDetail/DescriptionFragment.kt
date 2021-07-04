package com.example.filmapp.framework.main.ui.descriptionDetail

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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

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
            }
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