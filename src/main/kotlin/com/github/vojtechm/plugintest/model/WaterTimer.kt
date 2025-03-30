package com.github.vojtechm.plugintest.model

import com.github.vojtechm.plugintest.timer.Notification
import com.intellij.ui.components.JBLabel
import java.util.*
import javax.swing.ImageIcon

class WaterTimer(notification: Notification, interval: Long, imageLabel: JBLabel, imagesArray: Array<ImageIcon>)
    :
    Interval(notification, interval, imageLabel, imagesArray)
{
    override fun startInterval() {
        val timer = Timer();
        timer.scheduleAtFixedRate(object : TimerTask() {
            override fun run() {
                notification.showNotification()
                updateImage()

                if (currentImageIndex >= imagesArray.size - 1) {
                    timer.cancel()
                }
            }
        }, interval, interval)
    }
}