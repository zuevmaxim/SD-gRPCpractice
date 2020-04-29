package ru.sd.practice

import com.rabbitmq.client.ConnectionFactory


class Chat(private val name: String, private val interactor: Interactor, private val hostname: String) {
    private var factory: ConnectionFactory

    init {
        interactor.addConsumer { parcel ->
            send(parcel)
        }
        factory = ConnectionFactory().apply {
            host = hostname
        }
        receive()
    }

    fun send(parcel: Parcel) {
        factory.newConnection().use { connection ->
            connection.createChannel().use { channel ->
                channel.exchangeDeclare(name, "fanout")
                channel.basicPublish(name, "", null, parcel.toBytes())
            }
        }
    }

    private fun receive() {
        val connection = factory.newConnection()
        val channel = connection.createChannel()
        channel.exchangeDeclare(name, "fanout")
        val queueName: String = channel.queueDeclare().queue
        channel.queueBind(queueName, name, "")

        channel.basicConsume(queueName, { _, delivery ->
            val parcel = Parcel.fromBytes(delivery.body)
            interactor.addMessage(parcel)
        }) { _ -> }
    }
}
