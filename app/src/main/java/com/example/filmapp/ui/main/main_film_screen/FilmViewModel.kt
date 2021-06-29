package com.example.filmapp.ui.main.main_film_screen

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.filmapp.model.AppState
import com.example.filmapp.model.repository.Repository
import com.example.filmapp.model.repository.RepositoryImpl
import kotlinx.coroutines.*

class FilmViewModel : ViewModel(), CoroutineScope by MainScope() {
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
        launch {
            val job = async(Dispatchers.IO) { repositoryImpl.getFilmCollectionFromLocalStorage() }
            liveDataToObserve.value = AppState.Success(job.await())
        }
    }
}