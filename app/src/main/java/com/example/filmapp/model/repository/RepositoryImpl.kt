package com.example.filmapp.model.repository

import com.example.filmapp.R
import com.example.filmapp.model.entites.Film
import com.example.filmapp.model.rest.FilmLoader
import com.example.filmapp.model.rest.FilmLoader.loadFilmList
import java.lang.Exception


class RepositoryImpl : Repository {
    private val imageId = listOf(
        R.drawable.posters,
        R.drawable.kinkongposters,
        R.drawable.skaterposters,
        R.drawable.starwarsposters,
        R.drawable.starwarsposters
    )

    private fun init(): ArrayList<Film> {
        val films = ArrayList<Film>(50)
        for (i in 1..10) {
            val film = getFilmFromServer((550 + i).toString())
            if (film != null) {
                films.add(
                    film
                )
            }

        }

        return films
    }

    override fun getFilmFromServer(id: String): Film? {
        val dto = FilmLoader.loadFilmFromId(id)
        if (dto != null) {
            return Film(
                dto.id,
                dto.title,
                dto.overview,
                dto.status,
                dto.vote_average,
                dto.release_date,
                dto.runtime,
                dto.popularity,
                dto.backdrop_path,
                dto.budget,
                dto.revenue,
                dto.genres
            )
        } else {
            return null
        }

    }

    override fun getFilmCollectionFromServer() = init()

    private fun getFilmPopularCollection(): ArrayList<Film> {
        val filmsRes = ArrayList<Film>(50)
            val films = loadFilmList()
            for (dto in films?.results!!) {
                filmsRes.add(
                    Film(
                        dto.id,
                        dto.title,
                        dto.overview,
                        dto.status,
                        dto.vote_average,
                        dto.release_date,
                        dto.runtime,
                        dto.popularity,
                        dto.backdrop_path,
                        dto.budget,
                        dto.revenue,
                        dto.genres
                    )
                )
            }


        return filmsRes
    }

    override fun getPopularityFilmsFromServer() = getFilmPopularCollection()





    override fun getFilmCollectionFromLocalStorage() = init()


    override fun getFilmFromLocalStorage(): Film {
        TODO("Not yet implemented")
    }
}