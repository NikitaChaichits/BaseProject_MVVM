package com.baseproject.data.model.pojo.resetpassword

import com.google.gson.annotations.SerializedName

data class ResetPasswordRequest(
    @SerializedName("email") var email: String,
    @SerializedName("token") var token: String,
    @SerializedName("password") var password: String,
    @SerializedName("password_confirmation") var passwordConfirmation: String
)