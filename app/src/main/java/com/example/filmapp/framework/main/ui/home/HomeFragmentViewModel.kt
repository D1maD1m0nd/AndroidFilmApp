package com.example.filmapp.framework.main.ui.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.filmapp.framework.main.ui.home.Adapters.Item
import com.example.filmapp.model.AppState
import com.example.filmapp.model.entites.Film
import com.example.filmapp.model.entites.FilmsList
import com.example.filmapp.model.repository.Repository
import com.example.filmapp.model.repository.RepositoryImpl
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class HomeFragmentViewModel(
    private val repositoryImpl: Repository = RepositoryImpl(),
    val liveDataToObserve: MutableLiveData<AppState> = MutableLiveData(),
    var filtersGenre: ArrayList<Int> = ArrayList(CAPACITY_FILTER),
    private val map: MutableMap<String, Item> = mutableMapOf(),
    private var pagePopularCount: Int = 0,
    private var pageUpcomingCount: Int = 0,
) :
    ViewModel() {
    fun getPopularFilms() {
        liveDataToObserve.value = AppState.Loading
        repositoryImpl.getPopularityFilmsFromServer(++pagePopularCount, callBack)
    }

    fun getUpcomingFilm() {
        liveDataToObserve.value = AppState.Loading
        repositoryImpl.getUpcomingFilmFromServer(++pageUpcomingCount, callBack)
    }


    private val callBack = object :
        Callback<FilmsList> {
        override fun onResponse(call: Call<FilmsList>, response: Response<FilmsList>) {

            val serverResponse: FilmsList? = response.body()
            liveDataToObserve.postValue(
                if (response.isSuccessful && serverResponse != null) {
                    val url = response.raw().request().url().toString()
                    checkResponse(serverResponse, url)
                } else {
                    AppState.Error(Throwable(SERVER_ERROR))
                }
            )
        }

        override fun onFailure(call: Call<FilmsList>, t: Throwable) {
            liveDataToObserve.postValue(AppState.Error(Throwable(t.message ?: REQUEST_ERROR)))
        }

        private fun checkResponse(serverResponse: FilmsList, url: String): AppState {
            var fact = serverResponse.results
            if (filtersGenre.isNotEmpty()) {
                fact = fact.filter {
                    var isFilter = false
                    for (id in filtersGenre) {
                        if (it.genreIds.contains(id)) {
                            isFilter = true
                            break
                        }
                    }
                    isFilter
                } as ArrayList<Film>
            }

            return if (fact.isEmpty()) {
                AppState.Error(Throwable(CORRUPTED_DATA))
            } else {
                val category = getCategory(url) ?: ""
                val item = Item(fact, category)

                if (!map.containsKey(category)) {
                    map[category] = item
                } else {
                    map[category]?.films?.addAll(fact)
                }
                AppState.SuccessFilmsData(map)
            }
        }

        private fun getCategory(url: String): String? {

            return CATEGORY.find {
                url.contains(it)
            }
        }
    }

    companion object {
        private val CATEGORY = listOf("popular", "upcoming")
        private const val SERVER_ERROR = "Ошибка сервера"
        private const val REQUEST_ERROR = "Ошибка запроса на сервер"
        private const val CORRUPTED_DATA = "Неполные данные"
        private const val CAPACITY_FILTER = 10
    }
}