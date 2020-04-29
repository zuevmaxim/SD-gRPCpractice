package ru.sd.practice

import com.rabbitmq.client.ConnectionFactory


class Chat(private val name: String, private val interactor: Interactor, private val host: String) {
    init {
        interactor.addConsumer {
            parcel -> send(parcel)
        }
    }

    fun send(parcel: Parcel) {
        val factory = ConnectionFactory()
        factory.host = host
        factory.newConnection().use { connection ->
            connection.createChannel().use { channel ->
                channel.exchangeDeclare(name, "fanout")
                channel.basicPublish(name, "", null, parcel.toBytes())
            }
        }
    }

    fun receive() {
//        val factory = ConnectionFactory()
//        factory.host = "localhost"
//        val connection: Connection = factory.newConnection()
//        val channel: Channel = connection.createChannel()
//
//        channel.exchangeDeclare(EXCHANGE_NAME, "fanout")
//        val queueName: String = channel.queueDeclare().getQueue()
//        channel.queueBind(queueName, EXCHANGE_NAME, "")
//
//        println(" [*] Waiting for messages. To exit press CTRL+C")
//
//        val deliverCallback = DeliverCallback { consumerTag: String?, delivery: Delivery ->
//            val message = String(delivery.body, "UTF-8")
//            println(" [x] Received '$message'")
//        }
//        channel.basicConsume(queueName, true, deliverCallback, { consumerTag -> })
    }
}