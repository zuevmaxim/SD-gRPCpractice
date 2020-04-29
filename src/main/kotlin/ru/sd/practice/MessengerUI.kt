package ru.sd.practice

import javafx.application.Application
import javafx.geometry.Insets
import javafx.geometry.Orientation
import javafx.geometry.Pos
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

    private val chanels = HashMap<String, Int>();
    private val scene = Scene(pane, WIDTH, HEIGHT)
    private var name = ""
    private var hostname = ""

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
            padding = Insets(5.0, 5.0, 5.0, 5.0)
            add(Label("Name: "), 0, 0)
            add(nameField, 1, 0)
        }

        val hostField = TextField("localhost")
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

        val tabPane = TabPane().apply {
            padding = Insets(15.0, 15.0, 15.0, 15.0)
        }

        val newChanelField = TextField()
        val addChanelButton = Button("Add").apply {
            setOnAction {
                val tab = Tab()
                val pane = FlowPane().apply {
                    orientation = Orientation.VERTICAL
                }
                tab.content = pane
                tabPane.tabs.add(tab)
                val chanel = newChanelField.text
                tab.text = chanel
                if (chanels.containsKey(newChanelField.text)) {
                    tabPane.selectionModel.select(chanels[chanel]!!)
                } else {
                    start(newChanelField.text, pane)
                }
            }
        }

        val newChanelGrid = GridPane().apply {
            alignment = Pos.BOTTOM_RIGHT
            padding = Insets(5.0, 5.0, 5.0, 5.0)
            add(Label("Chanel: "), 0, 0)
            add(newChanelField, 1, 0)
            add(addChanelButton, 2, 0)
        }

        pane.children.addAll(tabPane, newChanelGrid)

        layout.children.addAll(nameGrid, hostGrid, okButton)
        newWindow.show()
    }

    private fun start(chanel: String, pane: FlowPane) {
        val ui = UIInteractor(name, pane)
        Chat(chanel, ui, hostname)
    }

    companion object {
        private const val HEIGHT = 600.0
        private const val WIDTH = 600.0
    }
}
