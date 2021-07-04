package com.example.filmapp.model.repository

import com.example.filmapp.model.entites.Film
import com.example.filmapp.model.entites.FilmDTO
import com.example.filmapp.model.entites.FilmsDTO

interface Repository {
    fun getFilmFromServer(id: String): Film?
    fun getFilmCollectionFromServer(): ArrayList<Film>
    fun getPopularityFilmsFromServer(callback: retrofit2.Callback<FilmsDTO>)
    fun getFilmCollectionFromLocalStorage(): ArrayList<Film>
    fun getFilmFromLocalStorage(): Film?
    fun convertDtoFromLocal(list : ArrayList<FilmDTO>) : ArrayList<Film>
}