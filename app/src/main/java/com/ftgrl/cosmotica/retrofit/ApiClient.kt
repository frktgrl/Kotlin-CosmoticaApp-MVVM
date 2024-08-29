package com.ftgrl.cosmotica.retrofit

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

object ApiClient {

    private var retrofit: Retrofit? = null

    fun getClient(Base_Url: String): Retrofit {

        if (retrofit == null) {
            retrofit = Retrofit.Builder()
                .baseUrl(Base_Url)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(CoroutineCallAdapterFactory()) // Coroutine Call Adapter Factory ekleyin
                .build()
        }

        return retrofit as Retrofit
    }

}