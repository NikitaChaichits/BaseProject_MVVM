package com.baseproject.data.model.pojo.login

import com.google.gson.annotations.SerializedName

data class LoginResponse(
    @SerializedName("data") val data: LoginData
)