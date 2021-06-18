package com.example.filmapp.model.repository

import com.example.filmapp.model.entites.Film

interface Repository {
    fun getFilmFromServer(): Film
    fun getFilmCollectionFromServer(): ArrayList<Film>
    fun getFilmCollectionFromLocalStorage(): ArrayList<Film>
    fun getFilmFromLocalStorage(): Film
}