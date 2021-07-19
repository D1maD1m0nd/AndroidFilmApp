package com.example.filmapp.framework.main.ui.main_film_screen

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.filmapp.model.AppState
import com.example.filmapp.model.entites.Film
import com.example.filmapp.model.repository.Repository
import kotlinx.coroutines.*

class FilmViewModel(private val repositoryImpl: Repository) : ViewModel(),
    CoroutineScope by MainScope() {

    private val liveDataToObserve: MutableLiveData<AppState> = MutableLiveData()


    fun getLiveData() = liveDataToObserve
    fun getFilmLocalSource() = getDataFromLocalSource()

    private fun getDataFromLocalSource() {
        liveDataToObserve.value = AppState.Loading
        launch {
            val job = async(Dispatchers.IO) { repositoryImpl.getAllHistory() }
            liveDataToObserve.value = AppState.Success(job.await() as ArrayList<Film>)
        }
    }
}