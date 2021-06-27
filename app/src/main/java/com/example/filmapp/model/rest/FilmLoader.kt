package com.example.filmapp.model.rest

import android.os.Build
import androidx.annotation.RequiresApi
import com.example.filmapp.model.entites.FilmDTO
import com.google.gson.Gson
import java.io.BufferedReader
import java.io.InputStreamReader
import java.lang.StringBuilder
import java.net.MalformedURLException
import java.net.URL
import java.util.stream.Collectors
import javax.net.ssl.HttpsURLConnection

object  FilmLoader {
    private const val API_KEY = "87c56b62284c5106bcde3abec2025d2a"

    @RequiresApi(Build.VERSION_CODES.N)
    private fun getLines(reader: BufferedReader): String {
        return reader.lines().collect(Collectors.joining("\n"))
    }

    private fun getLinesForOld(reader: BufferedReader) : String {
        val rowData = StringBuilder(1024)
        var tempVariable : String?

        while (reader.readLine().also { tempVariable = it } != null) {
            rowData.append(tempVariable).append("\n")
        }

        reader.close()
        return rowData.toString()
    }

    fun loadFilmFromId(id : String): FilmDTO? {
        try {
            val uri = URL("https://api.themoviedb.org/3/movie/$id?api_key=$API_KEY")

            lateinit var urlConnection: HttpsURLConnection
            try {
                urlConnection = uri.openConnection() as HttpsURLConnection
                urlConnection.requestMethod = "GET"
                urlConnection.readTimeout = 10000
                val bufferedReader = BufferedReader(InputStreamReader(urlConnection.inputStream))
                val lines = if(Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
                    getLinesForOld(bufferedReader)
                } else {
                    getLines(bufferedReader)
                }
                return Gson().fromJson(lines, FilmDTO::class.java)
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
