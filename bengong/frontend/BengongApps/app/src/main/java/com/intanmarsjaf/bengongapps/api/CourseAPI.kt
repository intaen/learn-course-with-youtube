package com.intanmarsjaf.bengongapps.api

import com.intanmarsjaf.bengongapps.model.Course
import com.intanmarsjaf.bengongapps.model.ResponseCourse
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface CourseAPI {
    @GET("/courses")
    fun getAllCourse(): Call<ResponseCourse>

    @POST("/course")
    fun insert(@Body request: RequestBody): Call<ResponseCourse>
}