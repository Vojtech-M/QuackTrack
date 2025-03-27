package com.github.vojtechm.plugintest.toolWindow

import com.github.vojtechm.plugintest.MyBundle.message
import com.github.vojtechm.plugintest.services.MyProjectService
import com.github.vojtechm.plugintest.timer.CountdownTimer
import com.intellij.openapi.diagnostic.thisLogger
import com.intellij.openapi.project.Project
import com.intellij.openapi.wm.ToolWindow
import com.intellij.openapi.wm.ToolWindowFactory
import com.intellij.ui.components.JBLabel
import com.intellij.ui.components.JBPanel
import com.intellij.ui.components.JBTextField
import com.intellij.ui.content.ContentFactory
import javax.swing.JButton
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
        private val label = JBLabel("Timer: 02:00:00") // Start from 2 hours
        val countdownTimer = CountdownTimer(label)
         // 2-hour countdown

        val content: JBPanel<*>
            get() {
                val panel: JBPanel<*> = JBPanel<JBPanel<*>>()
                val textField = JBTextField(20)
                val startTimer = JButton("Start Timer") // Updated button text
                val comboBox = JComboBox(arrayOf("1", "2", "3"))

                comboBox.selectedIndex = 1 // Select "2"
                panel.add(label) // Display countdown timer
                panel.add(textField)
                panel.add(startTimer)
                panel.add(comboBox)

                startTimer.addActionListener {
                    countdownTimer.sethours(comboBox.selectedIndex.toString().toInt() + 1) // Set hours from the combo box
                    countdownTimer.start() // Start the timer only when the button is clicked
                    startTimer.isEnabled = true  // Disable button after starting
                }


                return panel
            }
    }
}
