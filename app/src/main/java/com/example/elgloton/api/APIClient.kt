package com.example.elgloton.api

import com.example.elgloton.api.requests.APIService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object APIClient {
    private val retrofit = Retrofit.Builder()
        .baseUrl("http://192.168.1.10:8000/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val apiService = retrofit.create(APIService::class.java)
}