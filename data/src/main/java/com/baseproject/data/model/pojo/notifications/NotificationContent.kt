package com.baseproject.data.model.pojo.notifications

import com.baseproject.domain.model.dto.NotificationLevel
import com.google.gson.annotations.SerializedName

data class NotificationContent(
    @SerializedName("title") val title: String?,
    @SerializedName("text") val text: String?,
    @SerializedName("level") val level: NotificationLevel?,
//    @SerializedName("reference_type") val referenceType: ReferenceType,
//    @SerializedName("reference_id") val referenceId: String
)