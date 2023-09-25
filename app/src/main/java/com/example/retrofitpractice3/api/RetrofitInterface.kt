package com.example.retrofitpractice3.api

import com.example.retrofitpractice3.data.Root
import retrofit2.Call
import retrofit2.http.GET


interface RetrofitInterface {
    @GET("api/") // 실제 API 엔드포인트를 설정
    fun requestData(): Call<Root>
}