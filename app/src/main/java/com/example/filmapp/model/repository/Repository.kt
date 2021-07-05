package com.example.filmapp.model.repository

import com.example.filmapp.model.entites.Film
import com.example.filmapp.model.entites.FilmsList

interface Repository {
    fun getFilmFromServer(id: String): Film?
    fun getFilmCollectionFromServer(): ArrayList<Film>
    fun getPopularityFilmsFromServer(callback: retrofit2.Callback<FilmsList>)
    fun getFilmCollectionFromLocalStorage(): ArrayList<Film>
    fun getFilmFromLocalStorage(): Film?
}