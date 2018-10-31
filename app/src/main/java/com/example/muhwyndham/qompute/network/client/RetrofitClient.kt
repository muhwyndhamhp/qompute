package com.example.muhwyndham.qompute.network.client

import com.example.muhwyndham.qompute.data.model.Component
import com.example.muhwyndham.qompute.data.model.ComponentList
import com.example.muhwyndham.qompute.utils.BASE_URL
import com.google.gson.*
import com.google.gson.reflect.TypeToken
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.reflect.Type

object RetrofitClient {

    @Volatile
    private var instance: Retrofit? = null


    fun getInstance(): Retrofit {
        if(instance == null){
            instance = Retrofit.Builder().apply {
                baseUrl(BASE_URL)
                addConverterFactory(buildGsonConverter())
                addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            }.build()
        }
        return instance!!
    }

    private fun buildGsonConverter(): GsonConverterFactory {
        return GsonConverterFactory.create(GsonBuilder().registerTypeAdapter(ComponentList::class.java, ComponentDeserializer()).create())
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

    private class ComponentDeserializer : JsonDeserializer<ComponentList> {

        override fun deserialize(
            json: JsonElement?,
            typeOfT: Type?,
            context: JsonDeserializationContext?
        ): ComponentList {
            val componentType = object : TypeToken<Collection<Component>>(){}.type
            val components = Gson().fromJson<Collection<Component>>(json, componentType)
            return ComponentList.createComponentList(components)
        }

    }

}