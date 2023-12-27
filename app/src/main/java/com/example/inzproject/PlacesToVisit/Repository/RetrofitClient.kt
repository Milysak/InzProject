package com.example.inzproject.PlacesToVisit.Repository
import android.util.Log
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


object RetrofitClient {

    private const val BASE_URL = "https://maps.googleapis.com/"

    private val interceptor = Interceptor { chain ->
        val request = chain.request()
        val response = chain.proceed(request)
        // Logowanie adresu URL zapytania
        Log.d("RetrofitClient", "Request URL: ${request.url}")
        response
    }

    private val client = OkHttpClient.Builder()
        .addInterceptor(interceptor)
        .build()

    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
    }

    val placeApi: PlaceApi by lazy {
        retrofit.create(PlaceApi::class.java)
    }
}


object RetrofitClient2 {

    private const val BASE_URL = "https://maps.googleapis.com/"

    private val interceptor = Interceptor { chain ->
        val request = chain.request()
        val response = chain.proceed(request)
        // Logowanie adresu URL zapytania
        Log.d("RetrofitClient", "Request URL: ${request.url}")
        response
    }

    private val client = OkHttpClient.Builder()
        .addInterceptor(interceptor)
        .build()

    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
    }

    val coordinatesapi: CordinatesApi by lazy {
        retrofit.create(CordinatesApi::class.java)
    }
}