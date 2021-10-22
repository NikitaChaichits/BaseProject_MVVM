package com.baseproject.data.model.pojo.forgotpassword

import com.google.gson.annotations.SerializedName

data class ForgotPasswordRequest(
    @SerializedName("email") var login: String
)