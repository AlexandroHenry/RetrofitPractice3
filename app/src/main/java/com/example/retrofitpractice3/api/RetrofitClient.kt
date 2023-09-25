package com.example.retrofitpractice3.api

import com.example.retrofitpractice3.app.Config.Companion.BASE_URL
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object RetrofitClient {
    private var retrofit: Retrofit? = null

    fun instance(): Retrofit {
        if (retrofit == null) {
            synchronized(Retrofit::class.java) {
                retrofit = Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(okHttpClient())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
            }
        }
        return retrofit!!
    }

    private fun okHttpClient(): OkHttpClient {
        val httpLogginInterceptor = HttpLoggingInterceptor()
        httpLogginInterceptor.level = HttpLoggingInterceptor.Level.BODY

        return OkHttpClient().newBuilder().apply {
            addInterceptor(httpLogginInterceptor)
            connectTimeout(1000 * 10, TimeUnit.SECONDS)
            writeTimeout(1000 * 10, TimeUnit.SECONDS)
            readTimeout(1000 * 10, TimeUnit.SECONDS)
        }.build()
    }
}