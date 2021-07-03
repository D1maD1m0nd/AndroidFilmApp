package com.example.filmapp.di

import com.example.filmapp.framework.main.ui.home.HomeFragmentViewModel
import com.example.filmapp.framework.main.ui.main_film_screen.FilmViewModel
import com.example.filmapp.model.repository.Repository
import com.example.filmapp.model.repository.RepositoryImpl
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module


val appModule = module {
    single<Repository> { RepositoryImpl() }

    //View models
    viewModel { FilmViewModel(get()) }

    viewModel { HomeFragmentViewModel(get()) }
}
