package com.intanmarsjaf.bengongapps.model

import com.google.gson.annotations.SerializedName
import com.intanmarsjaf.bengongapps.model.User

class LoginResponse (status:Boolean,message:String,data: User){

    @SerializedName("status")
    var status:Boolean?=status
    @SerializedName("message")
    var message:String?=message
    @SerializedName("data")
    var data:User?=data

}

data class Login( var email:String, var password:String)