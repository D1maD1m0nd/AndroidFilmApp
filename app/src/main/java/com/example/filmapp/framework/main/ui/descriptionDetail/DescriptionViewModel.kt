package com.example.filmapp.framework.main.ui.descriptionDetail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.filmapp.model.AppState
import com.example.filmapp.model.entites.Film
import com.example.filmapp.model.repository.Repository
import com.example.filmapp.model.repository.RepositoryImpl
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class DescriptionViewModel(
    private val repositoryImpl: Repository = RepositoryImpl(),
    val liveDataToObserve: MutableLiveData<AppState> = MutableLiveData(),
) : ViewModel() {

    fun getFilmForId(id: Int) {
        liveDataToObserve.value = AppState.Loading
        repositoryImpl.getFilmFromId(id, callBack)
    }

    private val callBack = object :
        Callback<Film> {
        override fun onResponse(call: Call<Film>, response: Response<Film>) {
            val serverResponse: Film? = response.body()
            liveDataToObserve.postValue(
                if (response.isSuccessful && serverResponse != null) {
                    checkResponse(serverResponse)
                } else {
                    AppState.Error(Throwable(SERVER_ERROR))
                }
            )
        }

        override fun onFailure(call: Call<Film>, t: Throwable) {
            liveDataToObserve.postValue(AppState.Error(Throwable(t.message ?: REQUEST_ERROR)))
        }

        private fun checkResponse(serverResponse: Film): AppState {
            return if (serverResponse == null) {
                AppState.Error(Throwable(CORRUPTED_DATA))
            } else {
                repositoryImpl.saveEntity(serverResponse)
                AppState.SuccessId(serverResponse)
            }
        }
    }

    companion object {
        private const val SERVER_ERROR = "Ошибка сервер"
        private const val REQUEST_ERROR = "Ошибка запроса на сервер"
        private const val CORRUPTED_DATA = "Неполные данные"
    }
}