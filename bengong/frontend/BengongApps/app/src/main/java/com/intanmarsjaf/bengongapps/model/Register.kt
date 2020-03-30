package com.intanmarsjaf.bengongapps.model

import com.google.gson.annotations.SerializedName

class RegisterResponse (status:Boolean,message:String,data:Register){

    @SerializedName("status")
    var status:Boolean?=status
    @SerializedName("message")
    var message:String?=message
    @SerializedName("data")
    var data:Register?=data

}

data class Register(var name:String, var username:String, var email:String, var password:String)