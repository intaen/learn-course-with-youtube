package com.intanmarsjaf.bengongapps.api

import com.intanmarsjaf.bengongapps.model.Course
import com.intanmarsjaf.bengongapps.model.DataDetail
import com.intanmarsjaf.bengongapps.model.ResponseCourse
import com.intanmarsjaf.bengongapps.model.ResponseData
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface DataAPI {
    @GET("/data/user/{id}")
    fun getDataByUser(@Path("id") id: Int): Call<ResponseData>

    @POST("/data")
    fun insert(@Body data: DataDetail): Call<ResponseData>
}