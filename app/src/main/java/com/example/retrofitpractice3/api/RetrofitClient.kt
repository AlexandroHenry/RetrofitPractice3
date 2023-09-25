package com.example.retrofitpractice3.api

import com.example.retrofitpractice3.app.Config.Companion.BASE_URL
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object RetrofitClient {
    // Retrofit 객체를 저장할 변수, 초기에는 null
    private var retrofit: Retrofit? = null

    // Retrofit 객체를 반환하는 메서드
    fun instance(): Retrofit {
        // Retrofit 객체가 null인 경우에만 초기화 및 설정
        if (retrofit == null) {
            // 스레드 동기화를 통해 동시에 여러 스레드에서 Retrofit을 초기화하지 않도록 함
            synchronized(Retrofit::class.java) {
                retrofit = Retrofit.Builder()
                    // 기본 URL 설정, BASE_URL은 API의 기본 URL을 나타냄
                    .baseUrl(BASE_URL)
                    // HTTP 통신을 위한 OkHttpClient 객체 설정
                    .client(okHttpClient())
                    // JSON 데이터를 자바 객체로 변환하기 위한 Gson 변환기 설정
                    .addConverterFactory(GsonConverterFactory.create())
                    // Retrofit 객체 생성
                    .build()
            }
        }
        // 초기화된 또는 기존의 Retrofit 객체 반환
        return retrofit!!
    }

    // OkHttpClient 객체를 반환하는 메서드
    private fun okHttpClient(): OkHttpClient {
        // HTTP 요청 및 응답에 대한 로그를 출력하기 위한 인터셉터 생성
        val httpLoggingInterceptor = HttpLoggingInterceptor()
        // 로그 레벨 설정 (BODY는 요청 및 응답 본문까지 로그를 출력)
        httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

        // OkHttpClient 객체 생성 및 설정
        return OkHttpClient().newBuilder().apply {
            // 로깅 인터셉터를 OkHttpClient에 추가
            addInterceptor(httpLoggingInterceptor)
            // 연결 타임아웃 설정 (10초)
            connectTimeout(1000 * 10, TimeUnit.SECONDS)
            // 쓰기 타임아웃 설정 (10초)
            writeTimeout(1000 * 10, TimeUnit.SECONDS)
            // 읽기 타임아웃 설정 (10초)
            readTimeout(1000 * 10, TimeUnit.SECONDS)
        }.build()
    }
}
