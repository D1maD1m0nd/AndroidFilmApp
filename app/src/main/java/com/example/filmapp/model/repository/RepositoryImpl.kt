package com.example.filmapp.model.repository

import com.example.filmapp.R
import com.example.filmapp.model.entites.Film
import kotlin.random.Random


class RepositoryImpl : Repository {
    private val films = ArrayList<Film>(50)
    private val imageId = listOf<Int>(
        R.drawable.posters,
        R.drawable.kinkongposters,
        R.drawable.skaterposters,
        R.drawable.starwarsposters,
        R.drawable.starwarsposters
    )

    private fun init(): Repository {
        for (i in 1..40) {
            films.add(
                Film(
                    imageId[Random.nextInt(0, 4)],
                    "film #$i",
                    "Overview #$i",
                    "Released",
                    8.9,
                    "18.06.2021",
                    120,
                    30.5,
                    "Poster string",
                    5000000.0,
                    6000000.0
                )
            )
        }
        return this
    }

    override fun getFilmFromServer(): Film {
        TODO("Not yet implemented")
    }

    override fun getFilmCollectionFromServer(): ArrayList<Film> {
        return films
    }

    override fun getFilmCollectionFromLocalStorage(): ArrayList<Film> {
        init()
        return films
    }

    override fun getFilmFromLocalStorage(): Film {
        TODO("Not yet implemented")
    }
}