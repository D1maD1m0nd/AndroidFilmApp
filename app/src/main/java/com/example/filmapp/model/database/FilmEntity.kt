package com.example.filmapp.model.database

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity
data class FilmEntity(
    @PrimaryKey(autoGenerate = true) val id: Long,
    val idFilm: Int?,
    val title: String?,
    val overview: String?,
    val status: String?,
    val voteAverage: Double?,
    val dateReleased: String?,
    val runtime: Int?,
    val popularity: Double?,
    val poster: String?,
    val budget: Int?,
    val revenue: Int?,
    val genre: String?,
    val description: String
)
