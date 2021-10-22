package com.baseproject.data.model.pojo.notifications

import com.baseproject.domain.model.dto.NotificationCategory
import com.google.gson.annotations.SerializedName
import java.time.OffsetDateTime

data class NotificationData(
    @SerializedName("id") val id: String,
    @SerializedName("type") val type: NotificationCategory,
    @SerializedName("notifiable_type") val notifiableType: String,
    @SerializedName("notifiable_id") val notifiableId: String,
    @SerializedName("data") val content: NotificationContent,
    @SerializedName("read_at") val readAt: OffsetDateTime?,
    @SerializedName("created_at") val createdAt: OffsetDateTime,
    @SerializedName("updated_at") val updatedAt: OffsetDateTime
)