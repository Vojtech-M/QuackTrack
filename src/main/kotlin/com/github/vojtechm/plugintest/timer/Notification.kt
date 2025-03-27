package com.github.vojtechm.plugintest.timer

import com.intellij.notification.Notification
import com.intellij.notification.NotificationType
import com.intellij.notification.Notifications

class Notification (private val title: String, private val body: String){

    fun showNotification() {
        val notification = Notification(
            "Custom Notification Group", // ID skupiny
            title,
            body,
            NotificationType.INFORMATION // INFO, WARNING, ERROR
        )
        Notifications.Bus.notify(notification)
    }

}