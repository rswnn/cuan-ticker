package com.example.filmkita.data

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiClient {
    private const val API_BASE_URL = "https://api.coingecko.com/api/v3/"

    private var apiServices: ApiServices? = null

    fun build(): ApiServices? {

        var httpClient:OkHttpClient.Builder = OkHttpClient.Builder()
        httpClient.addInterceptor(interceptor())

        var builder: Retrofit.Builder = Retrofit.Builder()
            .baseUrl(API_BASE_URL).addConverterFactory(GsonConverterFactory.create())

        var retrofit: Retrofit = builder.client(httpClient.build()).build()
        apiServices = retrofit.create(ApiServices::class.java)

        return apiServices as ApiServices
    }

    fun interceptor(): HttpLoggingInterceptor {
        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY
        return logging
    }
}