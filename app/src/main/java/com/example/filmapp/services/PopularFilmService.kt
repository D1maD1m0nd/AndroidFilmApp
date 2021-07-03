package com.example.filmapp.services

import android.app.IntentService
import android.content.Intent
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.example.filmapp.BuildConfig.FILM_API_KEY
import com.example.filmapp.model.entites.Film
import com.example.filmapp.model.entites.FilmDTO
import com.example.filmapp.model.entites.FilmsDTO
import com.example.filmapp.services.ConstService.DETAILS_INTENT_FILTER
import com.example.filmapp.services.ConstService.DETAILS_LOAD_RESULT_EXTRA
import com.example.filmapp.services.ConstService.DETAILS_REQUEST_ERROR_EXTRA
import com.example.filmapp.services.ConstService.DETAILS_REQUEST_ERROR_MESSAGE_EXTRA
import com.example.filmapp.services.ConstService.DETAILS_RESPONSE_SUCCESS_EXTRA
import com.example.filmapp.services.ConstService.DETAILS_TEMP_EXTRA
import com.example.filmapp.services.ConstService.DETAILS_URL_MALFORMED_EXTRA
import com.google.gson.Gson
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.MalformedURLException
import java.net.URL
import java.util.stream.Collectors
import javax.net.ssl.HttpsURLConnection

class PopularFilmService(name : String = "PopularFilmService") : IntentService(name){
    companion object{
        const val POPULAR_FILMS = "POPULAR"
        const val TIMEOUT_CONNECTION = 1000
        const val CONNECT_METHOD = "GET"
    }
    private val broadcastIntent = Intent(DETAILS_INTENT_FILTER)

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onHandleIntent(intent: Intent?) {
            loadFilmList()
    }

    @RequiresApi(Build.VERSION_CODES.N)
    fun loadFilmList() {
        try {
            val uri =
                URL("https://api.themoviedb.org/3/movie/popular?api_key=${FILM_API_KEY}&language=en-US&page=1")
            lateinit var urlConnection: HttpsURLConnection
            try {
                urlConnection = uri.openConnection() as HttpsURLConnection
                urlConnection.requestMethod = CONNECT_METHOD
                urlConnection.readTimeout = TIMEOUT_CONNECTION
                val bufferedReader = BufferedReader(InputStreamReader(urlConnection.inputStream))
                val lines = if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
                    getLinesForOld(bufferedReader)
                } else {
                    getLines(bufferedReader)
                }
                onResponse(Gson().fromJson(lines, FilmsDTO::class.java))
            } catch (e: Exception) {
                onErrorRequest(e.message ?: "Empty error")
            }
            finally {
                urlConnection.disconnect()
            }
        } catch (e: MalformedURLException) {
            onMalformedURL()
        }
    }
    private fun onMalformedURL() {
        putLoadResult(DETAILS_URL_MALFORMED_EXTRA)
        LocalBroadcastManager.getInstance(this).sendBroadcast(broadcastIntent)
    }
    private fun onResponse(filmsDTO: FilmsDTO) {
        onSuccessResponse(filmsDTO.results)
    }

    private fun onSuccessResponse(results: ArrayList<FilmDTO>) {
        val list : ArrayList<Film> = ArrayList(50)
        for (dto in results) {
            list.add(
                Film(
                    dto.id,
                    dto.title,
                    dto.overview,
                    dto.status,
                    dto.vote_average,
                    dto.release_date,
                    dto.runtime,
                    dto.popularity,
                    dto.backdrop_path,
                    dto.budget,
                    dto.revenue,
                    dto.genres
                )
            )
        }
        putLoadResult(DETAILS_RESPONSE_SUCCESS_EXTRA)
        broadcastIntent.putExtra(DETAILS_TEMP_EXTRA, list)
        LocalBroadcastManager.getInstance(this).sendBroadcast(broadcastIntent)
    }
    private fun onErrorRequest(error: String) {
        putLoadResult(DETAILS_REQUEST_ERROR_EXTRA)
        broadcastIntent.putExtra(DETAILS_REQUEST_ERROR_MESSAGE_EXTRA, error)
        LocalBroadcastManager.getInstance(this).sendBroadcast(broadcastIntent)
    }
    @RequiresApi(Build.VERSION_CODES.N)
    private fun getLines(reader: BufferedReader): String {
        return reader.lines().collect(Collectors.joining("\n"))
    }

    private fun putLoadResult(result: String) {
        broadcastIntent.putExtra(DETAILS_LOAD_RESULT_EXTRA, result)
    }

    private fun getLinesForOld(reader: BufferedReader): String {
        val rowData = StringBuilder(1024)
        var tempVariable: String?

        while (reader.readLine().also { tempVariable = it } != null) {
            rowData.append(tempVariable).append("\n")
        }

        reader.close()
        return rowData.toString()
    }
}