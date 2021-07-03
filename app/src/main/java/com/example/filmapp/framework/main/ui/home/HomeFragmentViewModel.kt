package com.example.filmapp.framework.main.ui.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.filmapp.model.AppState
import com.example.filmapp.model.repository.Repository
import com.example.filmapp.model.repository.RepositoryImpl
import kotlinx.coroutines.*

class HomeFragmentViewModel(private val repositoryImpl: Repository = RepositoryImpl()) :
    ViewModel(), CoroutineScope by MainScope() {
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
            try {
                val job = async(Dispatchers.IO) { repositoryImpl.getPopularityFilmsFromServer() }
                liveDataToObserve.value = AppState.Success(job.await())
            } catch (e: Exception) {
                liveDataToObserve.value = AppState.Error(e)
            }

        }
    }
}