package com.example.filmapp.framework.main.ui.main_film_screen

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.filmapp.model.AppState
import com.example.filmapp.model.repository.Repository
import kotlinx.coroutines.*

class FilmViewModel(private val repositoryImpl: Repository) : ViewModel(),
    CoroutineScope by MainScope() {
    companion object {
        const val TIMEOUT = 1000L
    }

    // TODO: Implement the ViewModel
    private val liveDataToObserve: MutableLiveData<AppState> = MutableLiveData()


    fun getLiveData() = liveDataToObserve
    fun getFilmLocalSource() = getDataFromLocalSource()

    private fun getDataFromLocalSource() {
        liveDataToObserve.value = AppState.Loading
        launch {
            val job = async(Dispatchers.IO) { repositoryImpl.getFilmCollectionFromLocalStorage() }
            liveDataToObserve.value = AppState.Success(job.await())
        }
    }
}