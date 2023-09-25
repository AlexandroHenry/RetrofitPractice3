package com.example.retrofitpractice3.api

import android.content.Context
import com.example.retrofitpractice3.data.Root
import com.google.gson.JsonElement
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit

class RetrofitManager {

    companion object {
        // RetrofitManager의 인스턴스를 싱글톤으로 생성
        val instance = RetrofitManager()
    }

    // API에서 데이터를 가져오는 함수
    fun requestData(completion: (Root?) -> Unit) {
        // RetrofitClient를 통해 Retrofit 인터페이스를 생성하고 API 호출
        RetrofitClient.instance().create(RetrofitInterface::class.java).requestData().apply {
            // API 호출 결과를 비동기적으로 처리하기 위해 enqueue 사용
            enqueue(object : retrofit2.Callback<Root> {
                override fun onResponse(call: Call<Root>, response: retrofit2.Response<Root>) {
                    if (response.isSuccessful) {
                        // 성공적인 응답의 경우
                        val data = response.body() // API 응답에서 데이터 추출
                        completion(data) // 가져온 데이터를 완료 콜백에 전달
                    } else {
                        // 응답이 실패한 경우
                        completion(null) // 실패를 완료 콜백에 전달 (데이터 없음)
                    }
                }

                override fun onFailure(call: Call<Root>, t: Throwable) {
                    // 네트워크 요청 자체가 실패한 경우
                    completion(null) // 실패를 완료 콜백에 전달 (데이터 없음)
                }
            })
        }
    }
}
