package com.example.elgloton.api

import android.content.Context
import android.content.SharedPreferences
import com.example.elgloton.api.requests.dashboard.APIServiceCard
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object APIClientCard {
    private lateinit var sharedPreferences: SharedPreferences // Debes inicializar esto adecuadamente


    fun init(context: Context) {
        sharedPreferences = context.getSharedPreferences("myAppPrefs", Context.MODE_PRIVATE)

    }

    private val retrofit = Retrofit.Builder()
        .baseUrl("http://www.el-gloton.shop/")
        .addConverterFactory(GsonConverterFactory.create())
        .client(createOkHttpClient())
        .build()

    val apiService = retrofit.create(APIServiceCard::class.java)

    private fun createOkHttpClient(): OkHttpClient {
        val builder = OkHttpClient.Builder()
        builder.addInterceptor(createAuthorizationInterceptor())
        return builder.build()
    }

    private fun createAuthorizationInterceptor(): Interceptor {
        return Interceptor { chain ->
            val original = chain.request()
            val requestBuilder = original.newBuilder()
                .header("Authorization", "Bearer ${getAccessToken()}") // Obtiene el token de acceso de SharedPreferences
            val request = requestBuilder.build()
            chain.proceed(request)
        }
    }

    fun getAccessToken(): String ? {
        return sharedPreferences.getString("access_token", null)
    }
}
