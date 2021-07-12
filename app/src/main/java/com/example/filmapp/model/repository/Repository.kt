package com.example.filmapp.model.repository

import com.example.filmapp.model.entites.Film
import com.example.filmapp.model.entites.FilmsList
import retrofit2.Callback

interface Repository {
    fun getPopularityFilmsFromServer(page : Int, callback: Callback<FilmsList>)
    fun getFilmFromId(id: Int, callback: Callback<Film>)
    fun getFilmFromLocalStorage(): Film?
    fun saveEntity(film: Film)
    fun getAllHistory(): List<Film>
}