package com.example.filmapp.model.rest

import com.example.filmapp.model.entites.FilmDTO
import com.example.filmapp.model.entites.FilmsDTO
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface FilmApi {
    @GET("popular")
    fun getFilmDtoFromId(
        @Query("page") page: Int,
        @Query("language") langCode: String,
        @Query("api_key") key: String
    ): Call<FilmsDTO>
}