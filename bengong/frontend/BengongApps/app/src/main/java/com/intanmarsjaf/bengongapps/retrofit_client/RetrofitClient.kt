package com.intanmarsjaf.bengongapps.retrofit_client

import com.intanmarsjaf.bengongapps.api.UserAPI
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitClient {
    companion object {
        val BASE_URL = "http://10.0.2.2:5050"

        fun createRetrofit(): Retrofit {
            val retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create()).build()
            return retrofit
        }
    }
}