package com.baseproject.data.model.pojo.token

import com.baseproject.data.model.pojo.login.LoginData
import com.google.gson.annotations.SerializedName

data class TokenResponse(
    @SerializedName("data") val data: LoginData
)