package com.example.filmapp.framework.main.ui.home.Adapters

import com.example.filmapp.model.entites.Film

data class Item(
    val films: ArrayList<Film>,
    val category: String
)