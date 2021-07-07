package com.example.filmapp.model.rest

import com.example.filmapp.BuildConfig
import com.example.filmapp.model.entites.Film
import com.example.filmapp.model.entites.FilmsList
import com.example.filmapp.model.rest.utils.ApiUtils
import retrofit2.Callback
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object FilmRepository {
    private val api: FilmApi by lazy {
        val adapter = Retrofit.Builder()
            .baseUrl(ApiUtils.baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .client(ApiUtils.getOkHTTPBuilderWithHeaders())
            .build()

        adapter.create(FilmApi::class.java)
    }

    fun getFilms(page: Int, callback: Callback<FilmsList>) {
        api.getFilmDtoPopular(page).enqueue(callback)
    }

    fun getFilmForId(id: Int,  callback: Callback<Film>) {
        api.getFilmFromId(id).enqueue(callback)
    }
}