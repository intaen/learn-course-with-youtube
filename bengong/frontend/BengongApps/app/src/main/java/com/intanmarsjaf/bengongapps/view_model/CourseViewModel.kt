package com.intanmarsjaf.bengongapps.view_model

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.intanmarsjaf.bengongapps.api.CourseAPI
import com.intanmarsjaf.bengongapps.model.Course
import com.intanmarsjaf.bengongapps.model.ResponseCourse
import retrofit2.Call
import retrofit2.Response
import javax.security.auth.callback.Callback

class CourseViewModel: ViewModel() {
    val courses: MutableLiveData<List<Course>> by lazy {
        MutableLiveData<List<Course>>()
    }

    lateinit var courseAPI: CourseAPI

    fun getAllCourse(){
        courseAPI.getAllCourse().enqueue(object : retrofit2.Callback<ResponseCourse> {
            override fun onFailure(call: Call<ResponseCourse>, t: Throwable) {
                Log.e("[CourseViewModel.getAllCourse] Error occured : ", t.message)
            }

            override fun onResponse(call: Call<ResponseCourse>, response: Response<ResponseCourse>) {
                var responseBody = response?.body()
                courses.value = responseBody?.data
                println("")
            }
        })
    }
}