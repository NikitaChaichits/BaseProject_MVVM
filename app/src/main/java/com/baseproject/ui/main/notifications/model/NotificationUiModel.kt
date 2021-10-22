package com.baseproject.ui.main.notifications.model

import com.baseproject.domain.model.dto.Notification

data class NotificationUiModel(
    val notification: Notification,
    var expandedState: Boolean = false
) {
    val id = notification.id
    val title = notification.title
    val text = notification.text
    val updateTime = notification.updatedAt
}