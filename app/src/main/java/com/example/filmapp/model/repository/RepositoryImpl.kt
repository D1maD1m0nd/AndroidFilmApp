package com.example.filmapp.model.repository

import com.example.filmapp.R
import com.example.filmapp.model.entites.Film
import com.example.filmapp.model.entites.FilmDTO
import com.example.filmapp.model.rest.FilmLoader
import kotlin.random.Random


class RepositoryImpl : Repository {
    private val films = ArrayList<Film>(50)
    private val imageId = listOf(
        R.drawable.posters,
        R.drawable.kinkongposters,
        R.drawable.skaterposters,
        R.drawable.starwarsposters,
        R.drawable.starwarsposters
    )

    init {
        init()
    }
    private fun init(): Repository {
        Thread( Runnable{
            for (i in 1..10) {
                val film  = getFilmFromServer((550 + i).toString());
                films.add(
                    film
                )
            }
        }).start()

        return this
    }

    override fun getFilmFromServer(id: String): Film {
        val dto = FilmLoader.loadFilmFromId(id)
        return Film(
            dto?.id,
            dto?.title,
            dto?.overview,
            dto?.status,
            dto?.vote_average,
            dto?.release_date,
            dto?.runtime,
            dto?.popularity,
            dto?.backdrop_path,
            dto?.budget,
            dto?.revenue
        )
    }

    override fun getFilmCollectionFromServer() = films


    override fun getFilmCollectionFromLocalStorage() = films


    override fun getFilmFromLocalStorage(): Film {
        TODO("Not yet implemented")
    }
}