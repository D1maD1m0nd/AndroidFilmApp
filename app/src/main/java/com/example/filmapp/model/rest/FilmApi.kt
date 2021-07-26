package com.example.filmapp.model.rest

import com.example.filmapp.model.entites.Film
import com.example.filmapp.model.entites.FilmsList
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface FilmApi {
    @GET("popular")
    fun getFilmDtoPopular(
        @Query("page") page: Int
    ): Call<FilmsList>

    @GET("upcoming")
    fun getFilmUpcoming(
        @Query("page") page: Int
    ): Call<FilmsList>

    @GET("{id}")
    fun getFilmFromId(
        @Path("id") id: Int
    ): Call<Film>
}