package com.baseproject.data.model.pojo.login

import com.google.gson.annotations.SerializedName

data class LoginRequest(
    @SerializedName("email") var login: String,
    @SerializedName("password") var password: String
)