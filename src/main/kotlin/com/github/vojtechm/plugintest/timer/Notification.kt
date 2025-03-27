package com.github.vojtechm.plugintest.timer

import com.intellij.notification.Notification
import com.intellij.notification.NotificationType
import com.intellij.notification.Notifications

class Notification {
    fun showNotification() {
        val notification = Notification(
            "Custom Notification Group", // ID skupiny
            "d",
            "Toto je tělo notifikace.",
            NotificationType.INFORMATION // INFO, WARNING, ERROR
        )
        Notifications.Bus.notify(notification)
    }

}