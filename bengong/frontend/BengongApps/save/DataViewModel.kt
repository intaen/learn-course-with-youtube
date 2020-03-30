package com.intanmarsjaf.bengongapps.view_model

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.intanmarsjaf.bengongapps.api.DataAPI
import com.intanmarsjaf.bengongapps.model.Data
import com.intanmarsjaf.bengongapps.model.DataDetail
import com.intanmarsjaf.bengongapps.model.ResponseData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DataViewModel: ViewModel() {
    val datas: MutableLiveData<List<Data>> by lazy {
        MutableLiveData<List<Data>>()
    }

    lateinit var dataAPI: DataAPI
    lateinit var dataBody: DataDetail

    fun getDataByUser(id: Int){
        dataAPI.getDataByUser(id).enqueue(object : Callback<ResponseData> {
            override fun onFailure(call: Call<ResponseData>, t: Throwable) {
                Log.e("[DataViewModel.getDataByUser] Error occured : ", t.message)
            }

            override fun onResponse(call: Call<ResponseData>, response: Response<ResponseData>) {
                var responseBody = response?.body()
                datas.value = responseBody?.data
                println("")
            }
        })
    }

    fun insert(){
        dataAPI.insert(dataBody).enqueue(object : Callback<ResponseData> {
            override fun onFailure(call: Call<ResponseData>, t: Throwable) {
                Log.e("[DataViewModel.getByID] Error occured : ", t.message)
            }

            override fun onResponse(call: Call<ResponseData>, response: Response<ResponseData>) {
                var responseBody = response?.body()
                datas.value = responseBody?.data
                println("")
            }
        })
    }
}