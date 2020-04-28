package ru.sd.practice

import Main
import MessengerGrpc
import io.grpc.stub.StreamObserver

class ChatService(private val interactor: Interactor) : MessengerGrpc.MessengerImplBase() {
    override fun chat(responseObserver: StreamObserver<Main.Parcel>?): StreamObserver<Main.Parcel> {
        interactor.addConsumer {
            responseObserver?.onNext(it)
        }
        return MyMessageObserver(interactor)
    }
}

class MyMessageObserver(
    private val interactor: Interactor
) : StreamObserver<Main.Parcel> {
    override fun onNext(value: Main.Parcel?) {
        if (value == null) return
        interactor.addMessage(value)
    }

    override fun onError(t: Throwable?) {
        t?.printStackTrace()
    }

    override fun onCompleted() {
    }
}
