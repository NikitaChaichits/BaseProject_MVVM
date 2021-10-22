package com.baseproject.domain.model.dto

import java.time.OffsetDateTime

data class Notification(
    val id: String,
    val category: NotificationCategory,
    val title: String?,
    val text: String?,
//    val referenceType: ReferenceType,
//    val referenceId: String,
    val level: NotificationLevel?,
    val readTime: OffsetDateTime?,
    val createdAt: OffsetDateTime,
    val updatedAt: OffsetDateTime
)

enum class NotificationCategory { PasswordChanged, None }

enum class NotificationLevel { Success, Warning, Info, Error }

enum class ReferenceType { License, None }