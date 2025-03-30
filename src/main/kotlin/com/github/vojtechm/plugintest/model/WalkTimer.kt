package com.github.vojtechm.plugintest.model

import com.github.vojtechm.plugintest.timer.Notification
import com.intellij.ui.components.JBLabel
import java.util.*
import javax.swing.ImageIcon

class WalkTimer (notification: Notification, interval: Long, val label: JBLabel, imageLabel: JBLabel,
                 imagesArray: Array<ImageIcon>
)
    :
    Interval(notification, interval, imageLabel, imagesArray)
{
    override fun startInterval() {
        val timer = Timer();

        timer.scheduleAtFixedRate(object : TimerTask() {
            override fun run() {
                notification.showNotification()

                for (i in 0..50) {
                    label.text += " "
                    Thread.sleep(50)
                }
            }
        }, interval, interval)
    }
}