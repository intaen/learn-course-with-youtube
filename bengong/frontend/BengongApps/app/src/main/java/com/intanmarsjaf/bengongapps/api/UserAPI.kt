package com.intanmarsjaf.bengongapps.api

import com.intanmarsjaf.bengongapps.model.*
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*

interface UserAPI {

    @POST("/user/signup")
    fun register(@Body register: UserRegister): Call<ResponseUser>

    @POST("/user/login")
    fun login(@Body userLogin: UserLogin): Call<ResponseUser>

    @GET("user/{id}")
    fun getByID(@Path("id") id: Int): Call<ResponseUser>
}