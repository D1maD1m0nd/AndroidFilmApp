package com.example.filmapp.model.entites

data class FilmDTO(
    val id: Int?,
    val title: String?,
    val overview: String?,
    val status: String?,
    val vote_average: Double?,
    val release_date: String?,
    val runtime: Int?,
    val popularity: Double?,
    val backdrop_path: String?,
    val budget: Int?,
    val revenue: Int?,
    val genres : ArrayList<Genre>

) {

}