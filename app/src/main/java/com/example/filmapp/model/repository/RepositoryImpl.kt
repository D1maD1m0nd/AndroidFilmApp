package com.example.filmapp.model.repository

import com.example.filmapp.model.entites.Film
import com.example.filmapp.model.entites.FilmsList
import com.example.filmapp.model.rest.FilmLoader
import com.example.filmapp.model.rest.FilmRepository
import retrofit2.Callback


class RepositoryImpl : Repository {
    private val MAX_CAPACITY = 50

    private fun init(): ArrayList<Film> {
        val films = ArrayList<Film>(MAX_CAPACITY)
        for (i in 1..10) {
            getFilmFromServer((550 + i).toString())?.let {
                films.add(
                    it
                )
            }
        }

        return films
    }

    override fun getFilmFromServer(id: String): Film? {
        return FilmLoader.loadFilmFromId(id)

    }

    override fun getFilmCollectionFromServer() = init()

    override fun getPopularityFilmsFromServer(callback: Callback<FilmsList>) {
        FilmRepository.getFilms(1,  callback)
    }

    override fun getFilmFromId(id: Int, callback: Callback<Film>) {
        FilmRepository.getFilmForId(id,  callback)
    }

    override fun getFilmCollectionFromLocalStorage() = init()


    override fun getFilmFromLocalStorage(): Film {
        TODO("Not yet implemented")
    }
}