package com.example.filmapp.framework.main.ui.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.filmapp.model.AppState
import com.example.filmapp.model.entites.FilmsDTO
import com.example.filmapp.model.repository.Repository
import com.example.filmapp.model.repository.RepositoryImpl
import kotlinx.coroutines.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
private const val SERVER_ERROR = "Ошибка сервера"
private const val REQUEST_ERROR = "Ошибка запроса на сервер"
private const val CORRUPTED_DATA = "Неполные данные"
class HomeFragmentViewModel(private val repositoryImpl: Repository = RepositoryImpl(),
                            val liveDataToObserve: MutableLiveData<AppState> = MutableLiveData()) :
    ViewModel(), CoroutineScope by MainScope() {
    fun getPopularFilms() {
        liveDataToObserve.value = AppState.Loading
        repositoryImpl.getPopularityFilmsFromServer(callBack)
    }

    private val callBack = object :
        Callback<FilmsDTO> {
        override fun onResponse(call: Call<FilmsDTO>, response: Response<FilmsDTO>) {
            val serverResponse: FilmsDTO? = response.body()
            liveDataToObserve.postValue(
                if (response.isSuccessful && serverResponse != null) {
                    checkResponse(serverResponse)
                } else {
                    AppState.Error(Throwable(SERVER_ERROR))
                }
            )
        }

        override fun onFailure(call: Call<FilmsDTO>, t: Throwable) {
            liveDataToObserve.postValue(AppState.Error(Throwable(t.message ?: REQUEST_ERROR)))
        }

        private fun checkResponse(serverResponse: FilmsDTO): AppState {
            val fact = serverResponse.results
            return if (fact.isEmpty()) {
                AppState.Error(Throwable(CORRUPTED_DATA))
            } else {
                AppState.Success(repositoryImpl.convertDtoFromLocal(fact))
            }
        }
    }
}