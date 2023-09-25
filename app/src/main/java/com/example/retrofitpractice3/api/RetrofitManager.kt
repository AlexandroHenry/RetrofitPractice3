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
        val instance = RetrofitManager()
    }

    fun requestData(completion: (Root?) -> Unit) {

        RetrofitClient.instance().create(RetrofitInterface::class.java).requestData().apply {
            // API 호출
            enqueue(object : retrofit2.Callback<Root> {
                override fun onResponse(call: Call<Root>, response: retrofit2.Response<Root>) {
                    if (response.isSuccessful) {
                        val data = response.body()
                        completion(data)
                    } else {
                        completion(null)
                    }
                }

                override fun onFailure(call: Call<Root>, t: Throwable) {
                    completion(null)
                }
            })
        }
    }
}