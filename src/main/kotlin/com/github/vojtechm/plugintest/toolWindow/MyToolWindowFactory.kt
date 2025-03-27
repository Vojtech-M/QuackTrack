package com.github.vojtechm.plugintest.toolWindow

import com.github.vojtechm.plugintest.MyBundle.message
import com.github.vojtechm.plugintest.services.MyProjectService
import com.github.vojtechm.plugintest.timer.CountdownTimer
import com.github.vojtechm.plugintest.timer.Notification
import com.intellij.collaboration.ui.util.bindSelectedItemIn
import com.intellij.openapi.diagnostic.thisLogger
import com.intellij.openapi.project.Project
import com.intellij.openapi.ui.ComboBox
import com.intellij.openapi.wm.ToolWindow
import com.intellij.openapi.wm.ToolWindowFactory
import com.intellij.platform.diagnostic.telemetry.JPS
import com.intellij.ui.components.JBLabel
import com.intellij.ui.components.JBPanel
import com.intellij.ui.components.JBTextField
import com.intellij.ui.dsl.builder.*
import com.intellij.ui.content.ContentFactory
import javax.swing.JButton
import javax.swing.ImageIcon
import com.intellij.ui.dsl.builder.panel
import java.util.*

import javax.swing.JComboBox

class MyToolWindowFactory : ToolWindowFactory {
    init {
        thisLogger().warn("Don't forget to remove all non-needed sample code files with their corresponding registration entries in `plugin.xml`.")
    }

    override fun createToolWindowContent(project: Project, toolWindow: ToolWindow) {
        val myToolWindow = MyToolWindow(toolWindow)
        val content = ContentFactory.getInstance().createContent(myToolWindow.content, null, false)

        toolWindow.contentManager.addContent(content)
    }

    override fun shouldBeAvailable(project: Project): Boolean {
        return true
    }

    internal class MyToolWindow(toolWindow: ToolWindow) {
        private val service: MyProjectService = toolWindow.project.getService(MyProjectService::class.java)
        private val label = JBLabel("00:00:00") // Start from 2 hours
        private val waterlabel = JBLabel(null)
        public val stopNotification =  true
        val countdownTimer = CountdownTimer(label, Notification("cas", "vyprsel"), 0,0,0, false)
        val countdownTimerWater = CountdownTimer(waterlabel, Notification("voda", "napij se "), 0,0,5, stopNotification)

        private var imageLabel = JBLabel()
        private val imageIndexes = arrayOf("/icons/parek.png", "/icons/duck.png", "/icons/duck.png")
        private var currentImageIndex = 0
         // 2-hour countdown


        val content: JBPanel<*>
            get() {
                val imagePath = imageIndexes[currentImageIndex]

                val image1 = "/images/duck.gif"
                val image2 = "/images/duck2.gif"
                val image3 = "/images/duck3.gif"

                val imageIcon1 = ImageIcon(this::class.java.getResource(image1))
                val imageIcon2 = ImageIcon(this::class.java.getResource(image2))
                val imageIcon3 = ImageIcon(this::class.java.getResource(image3))

                val imagesArray = arrayOf(imageIcon1, imageIcon2, imageIcon3)


                imageLabel.icon = imageIcon1 // Set the image as the label's icon
                var selectedHour = "1"
                val box = JComboBox(arrayOf("1", "2", "3"))

                return panel {
                    // Row for the Timer Label
                    row("Timer") {
                      cell(label)
                        cell(box)
                        button("Start Timer") {
                            countdownTimer.sethours(box.selectedIndex.toString().toInt())
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
                            if (currentImageIndex <= imagesArray.size - 1) {
                                currentImageIndex = currentImageIndex + 1
                                imageLabel.icon = imagesArray[currentImageIndex]
                            }



                        }
                    }
                }
            }
    }
}
