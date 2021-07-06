package com.example.filmapp.model.rest

import android.os.Build
import androidx.annotation.RequiresApi
import com.example.filmapp.BuildConfig.FILM_API_KEY
import com.example.filmapp.model.entites.Film
import com.example.filmapp.model.entites.FilmsList
import com.google.gson.Gson
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.MalformedURLException
import java.net.URL
import java.util.stream.Collectors
import javax.net.ssl.HttpsURLConnection

object FilmLoader {
    private const val TIMEOUT_CONNECTION = 10000
    private const val REQUEST_METHOD = "GET"
    private const val BASE_URL = "https://api.themoviedb.org/3/movie/"
    private const val CAPACITY_STRING = 1024

    @RequiresApi(Build.VERSION_CODES.N)
    private fun getLines(reader: BufferedReader): String {
        return reader.lines().collect(Collectors.joining("\n"))
    }

    private fun getLinesForOld(reader: BufferedReader): String {
        val rowData = StringBuilder(CAPACITY_STRING)
        var tempVariable: String?

        while (reader.readLine().also { tempVariable = it } != null) {
            rowData.append(tempVariable).append("\n")
        }

        reader.close()
        return rowData.toString()
    }

    fun loadFilmList(): FilmsList? {
        try {
            val uri =
                URL("${BASE_URL}popular?api_key=$FILM_API_KEY&language=en-US&page=1")
            lateinit var urlConnection: HttpsURLConnection
            try {
                urlConnection = uri.openConnection() as HttpsURLConnection
                urlConnection.requestMethod = REQUEST_METHOD
                urlConnection.readTimeout = TIMEOUT_CONNECTION
                val bufferedReader = BufferedReader(InputStreamReader(urlConnection.inputStream))
                val lines = if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
                    getLinesForOld(bufferedReader)
                } else {
                    getLines(bufferedReader)
                }
                return Gson().fromJson(lines, FilmsList::class.java)
            } catch (e: Exception) {
                throw Exception()
            } finally {
                urlConnection.disconnect()
            }
        } catch (e: MalformedURLException) {
            e.printStackTrace()
        }

        return null
    }

    fun loadFilmFromId(id: String): Film? {
        try {
            val uri = URL("${BASE_URL}$id?api_key=$FILM_API_KEY")
            lateinit var urlConnection: HttpsURLConnection
            try {
                urlConnection = uri.openConnection() as HttpsURLConnection
                urlConnection.requestMethod = REQUEST_METHOD
                urlConnection.readTimeout = TIMEOUT_CONNECTION
                val bufferedReader = BufferedReader(InputStreamReader(urlConnection.inputStream))
                val lines = if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
                    getLinesForOld(bufferedReader)
                } else {
                    getLines(bufferedReader)
                }
                return Gson().fromJson(lines, Film::class.java)
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                urlConnection.disconnect()
            }
        } catch (e: MalformedURLException) {
            e.printStackTrace()
        }

        return null
    }
}
