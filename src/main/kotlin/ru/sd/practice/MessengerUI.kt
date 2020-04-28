package ru.sd.practice

import javafx.application.Application
import javafx.geometry.Insets
import javafx.geometry.Orientation
import javafx.scene.Scene
import javafx.scene.control.Button
import javafx.scene.control.Label
import javafx.scene.control.TextField
import javafx.scene.layout.FlowPane
import javafx.scene.layout.GridPane
import javafx.scene.layout.HBox
import javafx.stage.Stage


class MessengerUI : Application() {

    private val pane = FlowPane().apply {
        orientation = Orientation.VERTICAL
        vgap = 8.0
        hgap = 4.0
        padding = Insets(15.0, 15.0, 15.0, 15.0)
    }
    private val scene = Scene(pane, WIDTH, HEIGHT)
    private val name = ""

    override fun start(primaryStage: Stage) {
        primaryStage.scene = scene
        primaryStage.show()
        start()
    }

    fun start() {
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
            vgap = 4.0
            hgap = 10.0
            padding = Insets(5.0, 5.0, 5.0, 5.0)
            add(Label("Name: "), 0, 0)
            add(nameField, 1, 0)
        }
        val hostField = TextField()
        val hostGrid = GridPane().apply {
            isVisible = false
            vgap = 4.0
            hgap = 10.0
            padding = Insets(5.0, 5.0, 5.0, 5.0)
            add(Label("Host: "), 0, 0)
            add(hostField, 1, 0)
        }
        val portField = TextField()
        val portGrid = GridPane().apply {
            isVisible = false
            vgap = 4.0
            hgap = 10.0
            padding = Insets(5.0, 5.0, 5.0, 5.0)
            add(Label("Port: "), 0, 0)
            add(portField, 1, 0)
        }
        val serverButton = Button("Server").apply {
            isFocusTraversable = false
        }
        val clientButton = Button("Client").apply {
            isFocusTraversable = false
        }
        val hbox = HBox(serverButton, clientButton)
        var isServer = false
        val okButton = Button("Ok").apply {
            isFocusTraversable = false
            isVisible = false
            setOnAction {
                newWindow.close()
                if (isServer) {
                    startServer(nameField.text, portField.text.toInt())
                } else {
                    startClient(nameField.text, portField.text.toInt(), hostField.text)
                }
            }
        }
        val onButtonClicked = {
            serverButton.isVisible = false
            clientButton.isVisible = false
            portGrid.isVisible = true
            okButton.isVisible = true
        }
        serverButton.setOnAction {
            isServer = true
            onButtonClicked()
        }
        clientButton.setOnAction {
            isServer = false
            onButtonClicked()
            hostGrid.isVisible = true
        }
        layout.children.addAll(nameGrid, hbox, hostGrid, portGrid, okButton)
        newWindow.show()
    }

    private fun startServer(name: String, port: Int) {
        val ui = UIInteractor(name, pane)
        MessengerServer(port, ui).start()
    }

    private fun startClient(name: String, port: Int, host: String) {
        val ui = UIInteractor(name, pane)
        MessengerClient(host, port, ui).start()
    }

    companion object {
        private const val HEIGHT = 600.0
        private const val WIDTH = 600.0
    }
}
