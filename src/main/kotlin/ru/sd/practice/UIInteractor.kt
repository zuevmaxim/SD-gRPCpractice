package ru.sd.practice

import Main
import javafx.application.Platform
import javafx.geometry.Insets
import javafx.scene.control.Button
import javafx.scene.control.Label
import javafx.scene.control.TextField
import javafx.scene.layout.FlowPane
import javafx.scene.layout.HBox
import java.text.SimpleDateFormat
import java.util.*


class UIInteractor(username: String, private val layout: FlowPane) : Interactor {
    private val user = Main.User.newBuilder().setName(username).build()
    private val messageField = TextField()
    private val sendButton = Button("Send").apply {
        setOnAction {
            newMessage(messageField.text)
            messageField.clear()
        }
    }
    private val hBox = HBox(messageField, sendButton)

    init {
        layout.children.add(hBox)
    }

    override fun addMessage(message: Main.Parcel) {
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
            layout.children.addAll(hbox)
        }
    }

    private val consumers = mutableListOf<(Main.Parcel) -> Unit>()

    override fun addConsumer(consume: (Main.Parcel) -> Unit) {
        consumers.add(consume)
    }

    private fun newMessage(text: String) {
        val parcel = Main.Parcel.newBuilder()
            .setText(text)
            .setUser(user)
            .setTime(System.currentTimeMillis())
            .build()
        addMessage(parcel)
        for (consumer in consumers) {
            consumer(parcel)
        }
    }

    companion object {
        private val format = SimpleDateFormat("HH:mm")
        private fun timeLongToString(time: Long) = format.format(Date(time))
    }
}
