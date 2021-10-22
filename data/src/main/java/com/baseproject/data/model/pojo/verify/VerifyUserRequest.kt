package com.baseproject.data.model.pojo.verify

import com.google.gson.annotations.SerializedName

data class VerifyUserRequest(
    @SerializedName("token") var token: String,
    @SerializedName("password") var password: String,
    @SerializedName("password_confirmation") var passwordConfirmation: String
)