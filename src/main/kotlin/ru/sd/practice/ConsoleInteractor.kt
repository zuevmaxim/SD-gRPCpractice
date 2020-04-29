package ru.sd.practice

class ConsoleInteractor : Interactor {
    override fun addMessage(message: Parcel) {
        println(message.text)
    }

    private val consumers = mutableListOf<(Parcel) -> Unit>()
    override fun addConsumer(consume: (Parcel) -> Unit) {
        consumers.add(consume)
    }

    init {
        Thread{
            while (true) {
                val text = readLine()!!
                for (consumer in consumers) {
                    consumer(Parcel(System.currentTimeMillis(), User("SuperUser"), text))
                }
            }
        }.start()
    }
}