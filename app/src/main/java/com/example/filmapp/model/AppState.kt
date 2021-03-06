package com.example.filmapp.model

import com.example.filmapp.framework.main.ui.home.Adapters.Item
import com.example.filmapp.model.entites.Film

sealed class AppState {
    data class Success(val filmsData: ArrayList<Film>) : AppState()
    data class SuccessFilmsData(val filmData: MutableMap<String, Item>) : AppState()
    data class SuccessId(val filmData: Film) : AppState()
    data class Error(val error: Throwable) : AppState()
    object Loading : AppState()
}