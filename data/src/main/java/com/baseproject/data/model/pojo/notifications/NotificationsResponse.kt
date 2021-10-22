package com.baseproject.data.model.pojo.notifications

import com.google.gson.annotations.SerializedName

data class NotificationsResponse(
    @SerializedName("data") val data: List<NotificationData>
)