package com.baseproject.data.model.pojo.logout

import com.google.gson.annotations.SerializedName

data class LogoutResponse (
    @SerializedName("error") val error: Int,
    @SerializedName("data") val data: String
)