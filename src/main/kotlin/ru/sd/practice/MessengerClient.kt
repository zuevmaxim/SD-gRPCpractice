package ru.sd.practice

import MessengerGrpc
import io.grpc.ManagedChannel
import io.grpc.ManagedChannelBuilder


class MessengerClient(hostname: String, port: Int, private val interactor: Interactor) {
    private val channel: ManagedChannel = ManagedChannelBuilder.forAddress(hostname, port).usePlaintext().build()
    private val service: MessengerGrpc.MessengerStub = MessengerGrpc.newStub(channel)
    fun start() {
        Thread {
            val observer = service.chat(MyMessageObserver(interactor))
            interactor.addConsumer {
                observer?.onNext(it)
            }
        }.start()

    }
}
