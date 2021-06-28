package com.example.filmapp.ui.main.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.filmapp.model.AppState
import com.example.filmapp.model.repository.Repository
import com.example.filmapp.model.repository.RepositoryImpl
import com.example.filmapp.ui.main.main_film_screen.FilmViewModel

class HomeFragmentViewModel : ViewModel() {
    companion object {
        const val TIMEOUT = 1000L
    }

    // TODO: Implement the ViewModel
    private val liveDataToObserve: MutableLiveData<AppState> = MutableLiveData()
    private val repositoryImpl: Repository = RepositoryImpl()

    fun getLiveData() = liveDataToObserve
    fun getFilmLocalSource() = getDataFromLocalSource()

    private fun getDataFromLocalSource() {
        liveDataToObserve.value = AppState.Loading
        Thread {
            Thread.sleep(FilmViewModel.TIMEOUT)
            liveDataToObserve.postValue(AppState.Success(repositoryImpl.getPopularityFilmsFromServer()))
        }.start()
    }
}