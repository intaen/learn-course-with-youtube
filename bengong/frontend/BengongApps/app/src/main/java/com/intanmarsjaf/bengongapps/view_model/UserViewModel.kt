package com.intanmarsjaf.bengongapps.view_model

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.intanmarsjaf.bengongapps.api.UserAPI
import com.intanmarsjaf.bengongapps.model.ResponseUser
import com.intanmarsjaf.bengongapps.model.User
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserViewModel : ViewModel() {

    val user: MutableLiveData<User> by lazy {
        MutableLiveData<User>()
    }

    lateinit var userAPI: UserAPI

    fun getByID(id: Int) {
        userAPI.getByID(id).enqueue(object : Callback<ResponseUser> {
            override fun onFailure(call: Call<ResponseUser>, t: Throwable) {
                Log.e("[UserViewModel.getByID] Error occured : ", t.message)
            }

            override fun onResponse(call: Call<ResponseUser>, response: Response<ResponseUser>) {
                var responseJSON = response.body()
                user.value = responseJSON?.data
            }
        })
    }
}