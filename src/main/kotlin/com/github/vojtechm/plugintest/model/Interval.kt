package com.github.vojtechm.plugintest.model

import com.github.vojtechm.plugintest.timer.Notification
import com.intellij.ui.components.JBLabel
import java.util.*
import javax.swing.Icon
import javax.swing.ImageIcon

open class Interval (
    val notification: Notification,
    val interval: Long,
    private val imageLabel: JBLabel,
    val imagesArray: Array<ImageIcon>
    )
{
    var currentImageIndex = 0

    open fun startInterval() {
        val timer = Timer();
        timer.scheduleAtFixedRate(object : TimerTask() {
            override fun run() {
                notification.showNotification()
            }
        }, 0, interval)
    }

    fun updateImage(): Icon {
        if (currentImageIndex < imagesArray.size - 1) {
            currentImageIndex++
        }

        imageLabel.icon = imagesArray[currentImageIndex]
        return imageLabel.icon
    }

    fun restartCurrentImagesIndex(value: Int): Icon {
        this.currentImageIndex = value

        imageLabel.icon = imagesArray[currentImageIndex]
        return imageLabel.icon
    }
}