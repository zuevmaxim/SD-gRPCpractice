package ru.sd.practice

interface Interactor {
    fun addMessage(message: Main.Parcel)
    fun addConsumer(consume: (Main.Parcel) -> Unit)
}
