package ru.sd.practice

import io.grpc.ServerBuilder

class MessengerServer(port: Int, interactor: Interactor) {
    private val service = ChatService(interactor)
    private var server = ServerBuilder.forPort(port)
        .addService(service)
        .build()

    fun start() {
        server.start()
    }
}
