package com.github.vojtechm.plugintest.toolWindow

import com.github.vojtechm.plugintest.services.MyProjectService
import com.github.vojtechm.plugintest.timer.CountdownTimer
import com.github.vojtechm.plugintest.timer.Notification
import com.intellij.openapi.project.Project
import com.intellij.openapi.wm.ToolWindow
import com.intellij.openapi.wm.ToolWindowFactory
import com.intellij.ui.components.JBLabel
import com.intellij.ui.components.JBPanel
import com.intellij.ui.content.ContentFactory
import javax.swing.ImageIcon
import com.intellij.ui.dsl.builder.panel
import javax.swing.JComboBox

class MyToolWindowFactory : ToolWindowFactory {

    // This method is called when the tool window is created
    override fun createToolWindowContent(project: Project, toolWindow: ToolWindow) {
        val myToolWindow = MyToolWindow(toolWindow)
        val content = ContentFactory.getInstance().createContent(myToolWindow.content, null, false)
        toolWindow.contentManager.addContent(content)
    }


    // This method is called when the plugin is loaded
    override fun shouldBeAvailable(project: Project): Boolean {
        return true
    }
    internal class MyToolWindow(toolWindow: ToolWindow) {
        private val service: MyProjectService = toolWindow.project.getService(MyProjectService::class.java)
        private val label = JBLabel("00:00:00") // Start from 2 hours
        private val waterlabel = JBLabel(null)
        private var imageLabel = JBLabel()
        private var runningDuck = JBLabel()

        // Images
        val image1 = "/images/duck.gif"
        val image2 = "/images/duck2.gif"
        val image3 = "/images/duck3.gif"
        val image4 = "/images/runningDuck.gif"

        val imageIcon1 = ImageIcon(this::class.java.getResource(image1))
        val imageIcon2 = ImageIcon(this::class.java.getResource(image2))
        val imageIcon3 = ImageIcon(this::class.java.getResource(image3))
        val imageIcon4 = ImageIcon(this::class.java.getResource(image4))

        val imagesArray = arrayOf(imageIcon1, imageIcon2, imageIcon3)

        // Timers
        val countdownTimer = CountdownTimer(label, Notification("cas", "vyprsel"), 0,0,0, false, imageLabel, imagesArray)
        val countdownTimerWater = CountdownTimer(waterlabel, Notification("voda", "napij se "), 0,0,5, true, imageLabel, imagesArray)

        val content: JBPanel<*>
            get() {
                imageLabel.icon = imageIcon1 // Set the image as the label's icon
                val box = JComboBox(arrayOf("1", "2", "3"))

                return panel {
                    // Row for the Timer Label
                    row("Timer") {
                      cell(label)
                        cell(box)

                        button("Start Timer") {
                            countdownTimer.sethours(box.selectedIndex.toString().toInt() + 1)
                            countdownTimer.setMinutes(0)
                            countdownTimer.setSeconds(0)
                            countdownTimer.start()

                            countdownTimerWater.setSeconds(5)
                            countdownTimerWater.setMinutes(0)
                            countdownTimerWater.sethours(0)
                            countdownTimerWater.start()
                        }
                    }

                    // Row for Start Timer Button
                    row("") {
                        cell(imageLabel)
                        button("reset"){
                            imageLabel.icon = imageIcon1
                            countdownTimerWater.setCurrentImageIndex(0)
                        }
                    }

                    // row o animated duck
                    row("running ruck"){
                        cell(runningDuck)
                        runningDuck.icon = imageIcon4
                    }
                }
            }
    }
}