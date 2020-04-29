package ru.sd.practice

interface Interactor {
    fun addMessage(message: Parcel)
    fun addConsumer(consume: (Parcel) -> Unit)
}
