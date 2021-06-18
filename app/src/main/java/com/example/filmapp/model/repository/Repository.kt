package com.example.filmapp.model.repository

import com.example.filmapp.model.entites.Film

interface Repository {
    fun getFilmFromServer(): Film
    fun getFilmFromLocalStorage(): Film
}