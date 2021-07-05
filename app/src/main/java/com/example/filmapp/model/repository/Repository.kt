package com.example.filmapp.model.repository

import com.example.filmapp.model.entites.Film
import com.example.filmapp.model.entites.FilmsList
import retrofit2.Callback

interface Repository {
    fun getFilmFromServer(id: String): Film?
    fun getFilmCollectionFromServer(): ArrayList<Film>
    fun getPopularityFilmsFromServer(callback: Callback<FilmsList>)
    fun getFilmFromId(id : Int, callback: Callback<Film>)
    fun getFilmCollectionFromLocalStorage(): ArrayList<Film>
    fun getFilmFromLocalStorage(): Film?
}