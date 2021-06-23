package com.example.filmapp.model.repository

import com.example.filmapp.R
import com.example.filmapp.model.entites.Film
import kotlin.random.Random


class RepositoryImpl : Repository {
    private val films = ArrayList<Film>(50)
    private val imageId = listOf(
        R.drawable.posters,
        R.drawable.kinkongposters,
        R.drawable.skaterposters,
        R.drawable.starwarsposters,
        R.drawable.starwarsposters
    )

    init {
        init()
    }
    private fun init(): Repository {
        for (i in 1..40) {
            films.add(
                Film(
                    imageId[Random.nextInt(0, 4)],
                    "film #$i",
                    "Жизнь здорово потрепала нервишки Майкла Брайса, так что с карьерой телохранителя он решил завязать. Психотерапевт посоветовал ему отправиться на тихий курорт, вооружившись лишь книжкой и плейлистом расслабляющей музыки. Но и здесь его находит самая безумная в мире парочка: киллер мирового уровня и настоящий магнит неприятностей Дариус Кинкейд и его супруга Соня — буйная дамочка не робкого десятка. Преступный синдикат устроил на них охоту, и Майклу, при всем желании остаться в стороне, придется вернуться к старому ремеслу и снова стать телохранителем. На этот раз — жены киллера!",
                    "Released",
                    8.9,
                    "18.06.2021",
                    120,
                    30.5,
                    "Poster string",
                    5000000.0,
                    6000000.0
                )
            )
        }
        return this
    }

    override fun getFilmFromServer(): Film {
        TODO("Not yet implemented")
    }

    override fun getFilmCollectionFromServer() = films


    override fun getFilmCollectionFromLocalStorage() = films


    override fun getFilmFromLocalStorage(): Film {
        TODO("Not yet implemented")
    }
}