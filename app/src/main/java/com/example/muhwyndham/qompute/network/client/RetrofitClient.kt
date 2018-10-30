package com.example.muhwyndham.qompute.network.client

import com.example.muhwyndham.qompute.utils.BASE_URL
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {

    @Volatile
    private var instance: Retrofit? = null


    fun getInstance(): Retrofit {
        if(instance == null){
            instance = Retrofit.Builder().apply {
                baseUrl(BASE_URL)
                addConverterFactory(GsonConverterFactory.create(getGson()))
                client(getClient())
            }.build()
        }
        return instance!!
    }

    private fun getClient(): OkHttpClient {
        val interceptor = Interceptor{chain ->
            val newRequest = chain.request().newBuilder().build()
            chain.proceed(newRequest)
        }

        val builder = OkHttpClient.Builder()
        builder.interceptors().add(interceptor)
        return builder.build()

    }

    private fun getGson(): Gson {
        val builder = GsonBuilder()
        builder.excludeFieldsWithoutExposeAnnotation()
        return builder.create()
    }
}