package com.intanmarsjaf.bengongapps.view_model

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.intanmarsjaf.bengongapps.api.CourseAPI
import com.intanmarsjaf.bengongapps.api.TopicAPI
import com.intanmarsjaf.bengongapps.model.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TopicViewModel: ViewModel() {
    val topics: MutableLiveData<List<Topic>> by lazy {
        MutableLiveData<List<Topic>>()
    }

    lateinit var topicAPI: TopicAPI

    fun getTopicByCourse(id: Int){
        topicAPI.getTopicByCourse(id).enqueue(object : Callback<ResponseTopic> {
            override fun onFailure(call: Call<ResponseTopic>, t: Throwable) {
                Log.e("[TopicViewModel.getTopicByCourse] Error occured : ", t.message)
            }

            override fun onResponse(call: Call<ResponseTopic>, response: Response<ResponseTopic>) {
                var responseBody = response?.body()
                topics.value = responseBody?.data
                println("")
            }
        })
    }

    fun getByID(id: Int){
        topicAPI.getByID(id).enqueue(object : Callback<ResponseTopic> {
            override fun onFailure(call: Call<ResponseTopic>, t: Throwable) {
                Log.e("[TopicViewModel.getByID] Error occured : ", t.message)
            }

            override fun onResponse(call: Call<ResponseTopic>, response: Response<ResponseTopic>) {
                var responseBody = response?.body()
                topics.value = responseBody?.data
                println("")
            }
        })
    }
}