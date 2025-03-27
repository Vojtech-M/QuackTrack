package com.github.vojtechm.plugintest.timer

import com.intellij.ui.components.JBLabel
import javax.swing.JLabel
import java.util.Timer
import java.util.TimerTask
import javax.swing.Icon
import javax.swing.ImageIcon

class CountdownTimer(private val label: JLabel, private val Notification: Notification, private val hoursinput: Int, private val minutesinput: Int, private val secondsinput: Int, private val run: Boolean, private val imageLabel: JBLabel,private val imagesArray: Array<ImageIcon> ) {

    // default values
    private var timer = Timer()
    private var hours: Int = 0
    private var minutes: Int = 0
    private var seconds: Int = 0
    private var currentImageIndex = 0


    fun  start(): Boolean{
        reset()

        timer.scheduleAtFixedRate(object : TimerTask() {
            override fun run(){
                if (hours == 0 && minutes == 0 && seconds == 0) {
                    label.text = "Čas vypršel" // Display time's up message
                    Notification.showNotification()
                    hours = hoursinput
                    minutes = minutesinput
                    seconds = secondsinput

                    if (run == false){
                        timer.cancel()
                        return

                    } else {
                        reset()
                        imageLabel.icon = updateImage()
                        start()
                    }
                }
                decrementTime()
                label.text = formatTime() // Update label with countdown time
            }
        }, 0, 1000) // Start immediately, update every second
        return false
    }

    fun sethours(hours: Int) {
        this.hours = hours
    }
    fun setMinutes(minutes: Int) {
        this.minutes = minutes
    }
    fun setSeconds(seconds: Int) {
        this.seconds = seconds
    }

    fun getSeconds(): Int {
        return seconds
    }

    fun getMinutes(): Int {
        return minutes
    }

    fun getHours(): Int {
        return hours
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
        timer = Timer()
    }

    private fun updateImage(): Icon? {
        // Change the current image index
        if (currentImageIndex < imagesArray.size - 1) {
            currentImageIndex++
        }
        // Update the image label's icon
        imageLabel.icon = imagesArray[currentImageIndex]
        return imageLabel.icon
    }

    fun setCurrentImageIndex(index: Int){
        currentImageIndex = index
    }
}
