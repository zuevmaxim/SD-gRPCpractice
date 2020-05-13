package ru.sd.practice

import javafx.application.Platform
import javafx.geometry.Insets
import javafx.geometry.Pos
import javafx.scene.control.*
import javafx.scene.layout.*
import java.text.SimpleDateFormat
import java.util.*


class UIInteractor(username: String, layout: VBox) : Interactor {
    private val user = User(username)
    private val messageField = TextArea().apply {
        maxHeight = 100.0
        maxWidth = 250.0
    }
    private val sendButton = Button("Send").apply {
        setOnAction {
            newMessage(messageField.text)
            messageField.clear()
        }
    }
    private val hBox = HBox(messageField, sendButton).apply {
        alignment = Pos.BOTTOM_CENTER
        padding = Insets(10.0, 5.0, 10.0, 5.0)
    }

    private val messagesBox = VBox()

    init {
        val scrollPane = ScrollPane()
        VBox.setVgrow(scrollPane, Priority.ALWAYS)
        scrollPane.content = messagesBox
        scrollPane.maxWidth = Double.MAX_VALUE;
        layout.children.addAll(scrollPane, hBox)
    }

    override fun addMessage(message: Parcel) {
        Platform.runLater {
            val timeLabel = Label(timeLongToString(message.time)).apply {
                padding = Insets(5.0, 5.0, 5.0, 5.0)
            }
            val nameLabel = Label(message.user.name).apply {
                padding = Insets(5.0, 5.0, 5.0, 5.0)
            }
            val messageLabel = Label(message.text).apply {
                padding = Insets(5.0, 5.0, 5.0, 5.0)
            }
            val hbox = HBox(timeLabel, nameLabel, messageLabel)
            messagesBox.children.addAll(hbox)
        }
    }

    private val consumers = mutableListOf<(Parcel) -> Unit>()

    override fun addConsumer(consume: (Parcel) -> Unit) {
        consumers.add(consume)
    }

    private fun newMessage(text: String) {
        val parcel = Parcel(System.currentTimeMillis(), user, text)
        for (consumer in consumers) {
            consumer(parcel)
        }
    }

    companion object {
        private val format = SimpleDateFormat("HH:mm")
        private fun timeLongToString(time: Long) = format.format(Date(time))
    }
}
