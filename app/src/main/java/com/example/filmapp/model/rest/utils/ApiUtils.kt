package com.example.filmapp.model.rest.utils


import com.example.filmapp.BuildConfig
import okhttp3.HttpUrl
import okhttp3.OkHttpClient
import okhttp3.Request
import java.util.*
import java.util.concurrent.TimeUnit


object ApiUtils {
    private const val baseUrlMainPart = "https://api.themoviedb.org/"
    private const val baseUrlVersion = "3/"
    private const val baseUrlDirectory = "movie/"
    const val baseUrl = "$baseUrlMainPart$baseUrlVersion$baseUrlDirectory"

    fun getOkHTTPBuilderWithHeaders(): OkHttpClient {
        val httpClient = OkHttpClient.Builder()
        httpClient.connectTimeout(10, TimeUnit.SECONDS)
        httpClient.readTimeout(10, TimeUnit.SECONDS)
        httpClient.writeTimeout(10, TimeUnit.SECONDS)

        httpClient.addInterceptor { chain ->
            val original: Request = chain.request()
            val requestBuilder: Request.Builder = original.newBuilder()
                .url(
                    original.url().newBuilder()
                    .addQueryParameter("api_key", BuildConfig.FILM_API_KEY)
                        .addQueryParameter("language", Locale.getDefault().country)
                    .build())
                .method(original.method(), original.body())
            val request: Request = requestBuilder.build()
            chain.proceed(request)
        }

        return httpClient.build()
    }
}