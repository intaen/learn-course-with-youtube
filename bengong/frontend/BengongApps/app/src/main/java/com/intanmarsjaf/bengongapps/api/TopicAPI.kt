package com.intanmarsjaf.bengongapps.api

import com.intanmarsjaf.bengongapps.model.Course
import com.intanmarsjaf.bengongapps.model.ResponseTopic
import com.intanmarsjaf.bengongapps.model.Topic
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface TopicAPI {
    @GET("/topics")
    fun getAllTopic(): Call<ResponseTopic>

    @GET("/topic/lang/{id}")
    fun getTopicByCourse(@Path("id") id: Int): Call<ResponseTopic>

    @GET("/topic/{id}")
    fun getByID(@Path("id") id: Int): Call<ResponseTopic>
}