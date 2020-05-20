package ru.sd.practice

import javafx.application.Application
import javafx.geometry.Insets
import javafx.geometry.Orientation
import javafx.geometry.Pos
import javafx.scene.Scene
import javafx.scene.control.*
import javafx.scene.layout.*
import javafx.stage.Stage


class MessengerUI : Application() {

    private val pane = VBox().apply {
        maxHeight = Control.USE_PREF_SIZE
        prefWidth = WIDTH
    }

    private val chanels = HashMap<String, Int>()
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
            alignment = Pos.BOTTOM_CENTER
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

        val tabPane = TabPane()

        val newChanelField = TextField()
        val addChanelButton = Button("Add").apply {
            setOnAction {
                val chanel = newChanelField.text
                if (!chanels.containsKey(chanel)) {
                    val pane = VBox()
                    val tab = Tab().apply {
                        content = pane
                        text = chanel
                    }
                    VBox.setVgrow(tabPane, Priority.ALWAYS)
                    chanels[chanel] = tabPane.tabs.size
                    tabPane.tabs.add(tab)
                    start(chanel, pane)
                }
                tabPane.selectionModel.select(chanels[chanel]!!)
            }
        }

        val newChanelGrid = GridPane().apply {
            alignment = Pos.TOP_CENTER
            padding = Insets(10.0, 5.0, 10.0, 5.0)
            add(Label("Chanel: "), 0, 0)
            add(newChanelField, 1, 0)
            add(addChanelButton, 2, 0)
        }

        pane.children.addAll(newChanelGrid, tabPane)

        layout.children.addAll(nameGrid, hostGrid, okButton)
        newWindow.show()
    }

    private fun start(chanel: String, pane: VBox) {
        val ui = UIInteractor(name, pane)
        Chat(chanel, ui, hostname)
    }

    companion object {
        private const val HEIGHT = 600.0
        const val WIDTH = 600.0
    }
}
