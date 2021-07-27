package com.example.filmapp.model.repository

import com.example.filmapp.model.database.Database
import com.example.filmapp.model.database.FilmEntity
import com.example.filmapp.model.entites.Film
import com.example.filmapp.model.entites.FilmsList
import com.example.filmapp.model.entites.Genre
import com.example.filmapp.model.rest.FilmRepository
import kotlinx.coroutines.*
import okhttp3.Dispatcher
import retrofit2.Callback


class RepositoryImpl : Repository,
    CoroutineScope by MainScope() {
    private val MAX_PAGE = 500


    override fun getPopularityFilmsFromServer(page: Int, callback: Callback<FilmsList>) {
        if (MAX_PAGE >= page) {
            FilmRepository.getFilms(page, callback)
        }
    }

    override fun getFilmFromId(id: Int, callback: Callback<Film>) {
        FilmRepository.getFilmForId(id, callback)
    }

    override fun saveEntity(film: Film) {
        launch(Dispatchers.IO) {
           Database.db.filmDao().insert(convertFilmToEntity(film))
        }

    }

    override fun getAllHistory() = convertHistoryEntityToFilm(Database.db.filmDao().all())

    private fun convertHistoryEntityToFilm(entityList: List<FilmEntity>): List<Film> {
        return entityList.map {
            val genre: ArrayList<Genre> = ArrayList(5)
            it.genre?.split(",")?.forEach { param ->
                genre.add(Genre(0, param))
            }
            Film(
                it.idFilm,
                it.title,
                it.overview,
                it.status,
                it.voteAverage,
                it.dateReleased,
                it.runtime,
                it.popularity,
                it.poster,
                it.budget,
                it.revenue,
                genre
            )
        }
    }

    private fun convertFilmToEntity(film: Film): FilmEntity {
        return FilmEntity(
            film.id,
            film.title,
            film.overview,
            film.status,
            film.voteAverage,
            film.dateReleased,
            film.runtime,
            film.popularity,
            film.poster,
            film.budget,
            film.revenue,
            film.genre?.joinToString { it.name },
            ""
        )
    }
}