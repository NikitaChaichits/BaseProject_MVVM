package com.baseproject.data.model.pojo.login

import com.google.gson.annotations.SerializedName

data class LoginData(
    @SerializedName("access_token") val accessToken: String
)