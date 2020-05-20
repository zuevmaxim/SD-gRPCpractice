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
    private var username = ""
    private var hostname = ""
    private var password = ""

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

        val usernameField = TextField()
        val usernameGrid = GridPane().apply {
            padding = Insets(5.0, 5.0, 5.0, 5.0)
            add(Label("Username: "), 0, 0)
            add(usernameField, 1, 0)
        }

        val passwordField = TextField()
        val passwordGrid = GridPane().apply {
            padding = Insets(5.0, 5.0, 5.0, 5.0)
            add(Label("Password: "), 0, 0)
            add(passwordField, 1, 0)
        }

        val hostField = TextField("localhost")
        val hostGrid = GridPane().apply {
            padding = Insets(5.0, 5.0, 5.0, 5.0)
            add(Label("Host: "), 0, 0)
            add(hostField, 1, 0)
        }

        val okButton = Button("Ok").apply {
            setOnAction {
                username = usernameField.text
                password = passwordField.text
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

        layout.children.addAll(hostGrid, usernameGrid, passwordGrid, okButton)
        newWindow.show()
    }

    private fun start(chanel: String, pane: VBox) {
        val ui = UIInteractor(username, pane)
        Chat(chanel, ui, hostname) //TODO: use username and password here
    }

    companion object {
        private const val HEIGHT = 600.0
        const val WIDTH = 600.0
    }
}
