package ru.sd.practice

import javafx.application.Application
import javafx.geometry.Insets
import javafx.geometry.Orientation
import javafx.scene.Scene
import javafx.scene.control.*
import javafx.scene.layout.FlowPane
import javafx.scene.layout.GridPane
import javafx.stage.Stage


class MessengerUI : Application() {

    private val pane = FlowPane().apply {
        orientation = Orientation.VERTICAL
        vgap = 8.0
        hgap = 4.0
    }
    private val scene = Scene(pane, WIDTH, HEIGHT)
    private var name = ""
    private var hostname = ""

    override fun start(primaryStage: Stage) {
        primaryStage.scene = scene
        primaryStage.show()
        start()
    }

    fun start() {
        var tabPane = TabPane().apply {
            padding = Insets(15.0, 15.0, 15.0, 15.0)
        }
        pane.children.add(tabPane)

        val layout = FlowPane().apply {
            orientation = Orientation.VERTICAL
            vgap = 8.0
            hgap = 4.0
            padding = Insets(15.0, 15.0, 15.0, 15.0)
        }

        val scene = Scene(layout, 300.0, 300.0)
        val newWindow = Stage().apply {
            title = "Init"
            setScene(scene)
        }

        val nameField = TextField()
        val nameGrid = GridPane().apply {
            padding = Insets(5.0, 5.0, 5.0, 5.0)
            add(Label("Name: "), 0, 0)
            add(nameField, 1, 0)
        }

        val hostField = TextField()
        val hostGrid = GridPane().apply {
            padding = Insets(5.0, 5.0, 5.0, 5.0)
            add(Label("Host: "), 0, 0)
            add(hostField, 1, 0)
        }

        val okButton = Button("Ok").apply {
            setOnAction {
                name = nameField.text
                hostname = hostField.text
                newWindow.close()
            }
        }

        val newChanelField = TextField()
        val addChanelButton = Button("Add").apply {
            setOnAction {
                val tab = Tab()
                val pane = FlowPane().apply {
                    orientation = Orientation.VERTICAL
                }
                tab.content = pane
                start(name, hostname, newChanelField.text, pane)
            }
        }

        val newChanelGrid = GridPane().apply {
            padding = Insets(5.0, 5.0, 5.0, 5.0)
            add(Label("Chanel: "), 0, 0)
            add(newChanelField, 1, 0)
            add(addChanelButton, 2, 0)
        }

        pane.children.add(newChanelGrid)

        layout.children.addAll(tabPane, pane, nameGrid, hostGrid, okButton, newChanelGrid)
        newWindow.show()
    }

    private fun start(name: String, host: String, chanel: String, pane: FlowPane) {
        val ui = UIInteractor(name, pane)
//        Chat(hostname, chanel, ui).start()
    }

    companion object {
        private const val HEIGHT = 600.0
        private const val WIDTH = 600.0
    }
}
