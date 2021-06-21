package com.example.filmapp.model.entites

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize


/**
 * @author Avdoshin Dima
 *
 * @constructor
 *
 * @param id - идентификатор записи, необходим для запроса к сайту
 * @param title - заголвоок фильма
 * @param overview - описание фильма
 * @param status - Статус фильма, например выпущен или только ожидается
 * @param voteAverage - средняя оценка
 * @param dateReleased - дата релиза
 * @param runtime - продолжительность
 * @param popularity - средня популярность 1..100
 * @param poster - путь до изображения например site/pB8BM7pdSp6B6Ih7QZ4DrQ3PmJK.jpg
 * @param budget - бюджет
 * @param revenue - сборы
 */

@Parcelize
data class Film(
    val id: Int,
    val title: String,
    val overview: String,
    val status: String,
    val voteAverage: Double,
    val dateReleased: String,
    val runtime: Int,
    val popularity: Double,
    val poster: String,
    val budget: Double,
    val revenue: Double
) : Parcelable
