package com.example.filmapp.model.rest.utils


import com.example.filmapp.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.Request
import java.util.*
import java.util.concurrent.TimeUnit


object ApiUtils {
    private const val TIMEOUT = 10L
    private const val BASE_URL_MAIN = "https://api.themoviedb.org/"
    private const val BASE_API_VERSION = "3/"
    private const val BASE_DIRECTORY = "movie/"
    const val baseUrl = "$BASE_URL_MAIN$BASE_API_VERSION$BASE_DIRECTORY"

    fun getOkHTTPBuilderWithHeaders(): OkHttpClient {
        val httpClient = OkHttpClient.Builder()
        httpClient.connectTimeout(TIMEOUT, TimeUnit.SECONDS)
        httpClient.readTimeout(TIMEOUT, TimeUnit.SECONDS)
        httpClient.writeTimeout(TIMEOUT, TimeUnit.SECONDS)

        httpClient.addInterceptor { chain ->
            val original: Request = chain.request()
            val requestBuilder: Request.Builder = original.newBuilder()
                .url(
                    original.url().newBuilder()
                        .addQueryParameter("api_key", BuildConfig.FILM_API_KEY)
                        .addQueryParameter("language", Locale.getDefault().country)
                        .build()
                )
                .method(original.method(), original.body())
            val request: Request = requestBuilder.build()
            chain.proceed(request)
        }

        return httpClient.build()
    }
}