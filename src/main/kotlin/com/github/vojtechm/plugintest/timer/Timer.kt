package com.github.vojtechm.plugintest.timer

import com.intellij.notification.ActionCenter.showNotification
import javax.swing.JLabel
import java.util.Timer
import java.util.TimerTask

class CountdownTimer( private val label: JLabel) {
    private var timer = Timer()
    private var hours: Int = 0
    private var minutes: Int = 0
    private var seconds: Int = 0


    fun start() {
        reset()
        timer.scheduleAtFixedRate(object : TimerTask() {
            override fun run() {
                if (hours == 0 && minutes == 0 && seconds == 0) {
                    label.text = "Čas vypršel" // Display time's up message
                    Notification().showNotification()
                    timer.cancel() // Stop the timer
                    return
                }

                decrementTime()
                label.text = "Timer: ${formatTime()}" // Update label with countdown time
            }
        }, 0, 1000) // Start immediately, update every second
    }

    fun sethours(hours: Int) {
        this.hours = hours
    }


    private fun decrementTime() {
        if (seconds > 0) {
            seconds--
        } else if (minutes > 0) {
            minutes--
            seconds = 59
        } else if (hours > 0) {
            hours--
            minutes = 59
            seconds = 59
        }
    }

    private fun formatTime(): String {
        return String.format("%02d:%02d:%02d", hours, minutes, seconds)
    }

    fun reset() {
        timer.cancel()
        seconds = 0
        minutes = 0
        timer = Timer()
    }

}
